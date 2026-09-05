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
import com.app.gitgud.repository.FirestoreQuestionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RatingService {

    private static final double NEUTRAL_PERFORMANCE = 0.5;

    private final QuestionAttemptService questionAttemptService;
    private final FirestoreQuestionRepository questionRepository;
    private final CurriculumAccessService curriculumAccessService;

    private final int kFactor;

    public RatingService(
            QuestionAttemptService questionAttemptService,
            FirestoreQuestionRepository questionRepository,
            CurriculumAccessService curriculumAccessService,

            @Value("${gitgud.rating.k-factor}")
            int kFactor
    ) {
        this.questionAttemptService =
                questionAttemptService;

        this.questionRepository =
                questionRepository;

        this.curriculumAccessService =
                curriculumAccessService;

        this.kFactor =
                kFactor;
    }

    public RatingUpdate calculateRatingUpdate(
            String userId,
            QuizAttempt attempt,
            Quiz quiz,
            QuizResult result
    ) throws Exception {

        if (
                attempt.getStatus()
                        != QuizAttemptStatus.IN_PROGRESS
        ) {
            throw new IllegalStateException(
                    "Rating must be calculated before finalization"
            );
        }

        if (
                quiz.getType()
                        == QuizType.INTRODUCTORY
        ) {

            return calculateIntroductoryRating(
                    result
            );
        }

        if (
                quiz.getType()
                        == QuizType.REVIEW
        ) {

            return calculatePerformanceRating(
                    userId,
                    attempt,
                    quiz
            );
        }

        if (
                curriculumAccessService
                        .hasPreviousAttemptForQuiz(
                                userId,
                                quiz,
                                attempt.getId()
                        )
        ) {

            return RatingUpdate.noChange();
        }

        return calculatePerformanceRating(
                userId,
                attempt,
                quiz
        );
    }

    private RatingUpdate calculateIntroductoryRating(
            QuizResult result
    ) {

        int rating =
                switch (result.getGrade()) {

                    case FAILURE ->
                            1000;

                    case PASSING ->
                            1500;

                    case EXCELLENT ->
                            2200;

                    case PERFECT ->
                            2600;
                };

        return RatingUpdate.absolute(
                rating
        );
    }

    private RatingUpdate calculatePerformanceRating(
            String userId,
            QuizAttempt attempt,
            Quiz quiz
    ) throws Exception {

        if (
                attempt.getStatus()
                        == QuizAttemptStatus.CANCELLED
        ) {

            return RatingUpdate.change(
                    -kFactor / 2
            );
        }

        List<QuestionAttempt> questionAttempts =
                questionAttemptService
                        .getQuestionAttempts(
                                userId,
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

        return RatingUpdate.change(
                ratingChange
        );
    }

    private double calculateWeightedPerformance(
            List<QuestionAttempt> questionAttempts,
            Map<String, Question> questions
    ) {

        double earnedWeight = 0.0;
        double possibleWeight = 0.0;

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

            possibleWeight += weight;

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

    public static class RatingUpdate {

        private final Integer ratingChange;
        private final Integer absoluteRating;

        private RatingUpdate(
                Integer ratingChange,
                Integer absoluteRating
        ) {
            this.ratingChange = ratingChange;
            this.absoluteRating = absoluteRating;
        }

        public static RatingUpdate change(
                int ratingChange
        ) {
            return new RatingUpdate(
                    ratingChange,
                    null
            );
        }

        public static RatingUpdate absolute(
                int rating
        ) {
            return new RatingUpdate(
                    null,
                    rating
            );
        }

        public static RatingUpdate noChange() {
            return new RatingUpdate(
                    null,
                    null
            );
        }

        public Integer getRatingChange() {
            return ratingChange;
        }

        public Integer getAbsoluteRating() {
            return absoluteRating;
        }
    }
}
