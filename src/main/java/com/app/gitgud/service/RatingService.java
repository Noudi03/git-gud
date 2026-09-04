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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RatingService {

    private static final double NEUTRAL_PERFORMANCE = 0.5;

    private final UserService userService;
    private final QuestionAttemptService questionAttemptService;
    private final FirestoreQuestionRepository questionRepository;
    private final QuizAttemptService quizAttemptService;
    private final CurriculumAccessService curriculumAccessService;

    private final int kFactor;


    public RatingService(
            UserService userService,
            QuestionAttemptService questionAttemptService,
            FirestoreQuestionRepository questionRepository,
            QuizAttemptService quizAttemptService,
            CurriculumAccessService curriculumAccessService,

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

        this.curriculumAccessService =
                curriculumAccessService;

        this.kFactor =
                kFactor;
    }


    public void applyRating(
            String userId,
            QuizAttempt attempt,
            Quiz quiz,
            QuizResult result
    ) throws Exception {

        if (
                attempt.getStatus()
                        != QuizAttemptStatus.COMPLETED
                        && attempt.getStatus()
                        != QuizAttemptStatus.CANCELLED
        ) {
            throw new IllegalStateException(
                    "Cannot apply rating to in-progress attempt"
            );
        }

        boolean claimed =
                quizAttemptService.markOutcomeProcessed(
                        userId,
                        attempt.getId()
                );

        if (!claimed) {
            return;
        }

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
                    user,
                    result
            );

            return;
        }

        if (
                quiz.getType()
                        == QuizType.REVIEW
        ) {

            applyPerformanceRating(
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
                quiz
        );
    }


    private void applyIntroductoryRating(
            User user,
            QuizResult result
    ) throws Exception {

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

        userService.setRating(
                user.getId(),
                rating
        );
    }


    private void applyCurriculumRating(
            String userId,
            User user,
            QuizAttempt attempt,
            Quiz quiz
    ) throws Exception {

        if (
                curriculumAccessService
                        .hasPreviousAttemptForQuiz(
                                userId,
                                quiz,
                                attempt.getId()
                        )
        ) {

            return;
        }

        applyPerformanceRating(
                user,
                attempt,
                quiz
        );
    }


    private void applyPerformanceRating(
            User user,
            QuizAttempt attempt,
            Quiz quiz
    ) throws Exception {

        if (
                attempt.getStatus()
                        == QuizAttemptStatus.CANCELLED
        ) {

            changeRating(
                    user,
                    -kFactor / 2
            );

            return;
        }

        List<QuestionAttempt> questionAttempts =
                questionAttemptService
                        .getQuestionAttempts(
                                user.getId(),
                                attempt.getId()
                        );

        Map<String, Question> questions =
                new HashMap<>();

        for (
                QuestionAttempt questionAttempt
                : questionAttempts
        ) {

            Question question =
                    questionRepository.findQuestion(
                            quiz,
                            questionAttempt.getQuestionId()
                    );

            if (question == null) {
                throw new IllegalStateException(
                        "Question does not belong to quiz"
                );
            }

            questions.put(
                    question.getId(),
                    question
            );
        }

        double performance =
                calculateWeightedPerformance(
                        questionAttempts,
                        questions
                );

        int ratingChange =
                (int) Math.round(
                        kFactor
                                * (
                                performance
                                        - NEUTRAL_PERFORMANCE
                        )
                );

        changeRating(
                user,
                ratingChange
        );
    }


    private double calculateWeightedPerformance(
            List<QuestionAttempt> questionAttempts,
            Map<String, Question> questions
    ) {

        double earnedWeight =
                0.0;

        double possibleWeight =
                0.0;

        for (
                QuestionAttempt questionAttempt
                : questionAttempts
        ) {

            Question question =
                    questions.get(
                            questionAttempt.getQuestionId()
                    );

            double weight =
                    getDifficultyWeight(
                            question.getDifficulty()
                    );

            possibleWeight +=
                    weight;

            if (questionAttempt.isCorrect()) {
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

            case EASY -> 1.0;
            case MEDIUM -> 2.0;
            case HARD -> 3.0;
        };
    }


    private void changeRating(
            User user,
            int ratingChange
    ) throws Exception {

        userService.changeRating(
                user.getId(),
                ratingChange
        );
    }
}
