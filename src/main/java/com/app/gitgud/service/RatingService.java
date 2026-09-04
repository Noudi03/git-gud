package com.app.gitgud.service;

import com.app.gitgud.model.Question;
import com.app.gitgud.model.QuestionAttempt;
import com.app.gitgud.model.QuestionDifficulty;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizAttemptStatus;
import com.app.gitgud.model.QuizGrade;
import com.app.gitgud.model.QuizResult;
import com.app.gitgud.model.QuizType;
import com.app.gitgud.model.User;
import com.app.gitgud.repository.FirestoreQuestionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final UserService userService;
    private final QuestionAttemptService questionAttemptService;
    private final FirestoreQuestionRepository questionRepository;
    private final QuizAttemptService quizAttemptService;
    private final QuizResultService quizResultService;

    private final int kFactor;


    public RatingService(
            UserService userService,
            QuestionAttemptService questionAttemptService,
            FirestoreQuestionRepository questionRepository,
            QuizAttemptService quizAttemptService,
            QuizResultService quizResultService,

            @Value("${gitgud.rating.k-factor}")
            int kFactor
    ) {

        this.userService =
                userService;

        this.questionAttemptService =
                questionAttemptService;

        this.questionRepository =
                questionRepository;

        this.quizAttemptService =
                quizAttemptService;

        this.quizResultService =
                quizResultService;

        this.kFactor =
                kFactor;
    }


    public void applyRating(
            String userId,
            QuizAttempt attempt,
            Quiz quiz,
            QuizResult result
    ) throws Exception {

        User user =
                userService.getUser(
                        userId
                );

        if (user == null) {
            throw new IllegalArgumentException(
                    "User not found"
            );
        }

        if (
                quiz.getType()
                        == QuizType.INTRODUCTORY
        ) {

            applyIntroductoryRating(
                    userId,
                    user,
                    attempt,
                    result
            );

            return;
        }

        if (
                quiz.getType()
                        == QuizType.REVIEW
        ) {

            applyPerformanceRating(
                    userId,
                    user,
                    attempt,
                    quiz
            );

            return;
        }

        applyCurriculumRating(
                userId,
                user,
                attempt,
                quiz,
                result
        );
    }


    private void applyIntroductoryRating(
            String userId,
            User user,
            QuizAttempt attempt,
            QuizResult result
    ) throws Exception {

        if (
                hasAnyIntroductoryAttempt(
                        userId
                )
        ) {
            return;
        }

        int rating =
                switch (result.getGrade()) {

                    case FAILURE ->
                            User.DEFAULT_RATING;

                    case PASSING ->
                            1500;

                    case EXCELLENT ->
                            2200;

                    case PERFECT ->
                            2600;
                };

        userService.updateRating(
                user.getId(),
                rating
        );
    }


    private void applyCurriculumRating(
            String userId,
            User user,
            QuizAttempt attempt,
            Quiz quiz,
            QuizResult result
    ) throws Exception {

        if (
                hasPreviousPassingAttempt(
                        userId,
                        quiz,
                        attempt.getId()
                )
        ) {
            return;
        }

        applyPerformanceRating(
                userId,
                user,
                attempt,
                quiz
        );
    }


    private void applyPerformanceRating(
            String userId,
            User user,
            QuizAttempt attempt,
            Quiz quiz
    ) throws Exception {

        List<QuestionAttempt> questionAttempts =
                questionAttemptService
                        .getQuestionAttempts(
                                userId,
                                attempt.getId()
                        );

        if (
                attempt.getStatus()
                        == QuizAttemptStatus.CANCELLED
        ) {

            updateRating(
                    user,
                    -kFactor / 2
            );

            return;
        }

        List<Question> questions =
                questionAttempts.stream()
                        .map(questionAttempt -> {

                            try {
                                return questionRepository
                                        .findQuestion(
                                                quiz,
                                                questionAttempt
                                                        .getQuestionId()
                                        );
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                        })
                        .filter(question -> question != null)
                        .toList();

        double performance =
                calculateWeightedPerformance(
                        questionAttempts,
                        questions
                );

        int ratingChange =
                (int) Math.round(
                        kFactor
                                * (performance - 0.5)
                );

        updateRating(
                user,
                ratingChange
        );
    }


    private double calculateWeightedPerformance(
            List<QuestionAttempt> questionAttempts,
            List<Question> questions
    ) {

        double earnedWeight =
                0.0;

        double possibleWeight =
                0.0;

        for (Question question : questions) {

            double weight =
                    getDifficultyWeight(
                            question.getDifficulty()
                    );

            possibleWeight +=
                    weight;

            boolean correct =
                    questionAttempts.stream()
                            .filter(attempt ->
                                    attempt.getQuestionId()
                                            .equals(
                                                    question.getId()
                                            )
                            )
                            .anyMatch(
                                    QuestionAttempt::isCorrect
                            );

            if (correct) {
                earnedWeight += weight;
            }
        }

        if (possibleWeight == 0) {
            return 0.0;
        }

        return earnedWeight / possibleWeight;
    }


    private double getDifficultyWeight(
            QuestionDifficulty difficulty
    ) {

        return switch (difficulty) {

            case EASY ->
                    1.0;

            case MEDIUM ->
                    2.0;

            case HARD ->
                    3.0;
        };
    }


    private boolean hasPreviousPassingAttempt(
            String userId,
            Quiz quiz,
            String currentAttemptId
    ) throws Exception {

        List<QuizAttempt> attempts =
                quizAttemptService.getAttempts(
                        userId
                );

        for (QuizAttempt attempt : attempts) {

            if (
                    attempt.getId()
                            .equals(currentAttemptId)
            ) {
                continue;
            }

            if (
                    !attempt.getQuizId()
                            .equals(quiz.getId())
            ) {
                continue;
            }

            if (
                    attempt.getStatus()
                            != QuizAttemptStatus.COMPLETED
            ) {
                continue;
            }

            QuizResult result =
                    quizResultService.getResult(
                            userId,
                            attempt.getId()
                    );

            if (
                    result.getGrade()
                            != QuizGrade.FAILURE
            ) {
                return true;
            }
        }

        return false;
    }


    private boolean hasAnyIntroductoryAttempt(
            String userId
    ) throws Exception {

        List<QuizAttempt> attempts =
                quizAttemptService.getAttempts(
                        userId
                );

        for (QuizAttempt attempt : attempts) {

            Quiz quiz =
                    quizAttemptService.getQuiz(
                            attempt
                    );

            if (
                    quiz.getType()
                            == QuizType.INTRODUCTORY
            ) {
                return true;
            }
        }

        return false;
    }


    private void updateRating(
            User user,
            int ratingChange
    ) throws Exception {

        int newRating =
                Math.max(
                        0,
                        user.getRating()
                                + ratingChange
                );

        userService.updateRating(
                user.getId(),
                newRating
        );
    }
}
