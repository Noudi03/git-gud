package com.app.gitgud.service;

import com.app.gitgud.config.GitGudProperties;
import com.app.gitgud.model.Question;
import com.app.gitgud.model.QuestionAttempt;
import com.app.gitgud.model.QuestionDifficulty;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizAttemptStatus;
import com.app.gitgud.model.QuizGrade;
import com.app.gitgud.model.QuizResult;
import com.app.gitgud.repository.FirestoreQuestionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuizResultService {

    private final QuizAttemptService quizAttemptService;
    private final QuestionAttemptService questionAttemptService;
    private final FirestoreQuestionRepository questionRepository;
    private final GitGudProperties properties;

    private final int basePoints;
    private final double hintPenalty;
    private final double timePenalty;
    private final int freeTimeSeconds;
    private final int skipPenalty;

    public QuizResultService(
            QuizAttemptService quizAttemptService,
            QuestionAttemptService questionAttemptService,
            FirestoreQuestionRepository questionRepository,
            GitGudProperties properties,

            @Value("${gitgud.quiz-scoring.base-points}")
            int basePoints,

            @Value("${gitgud.quiz-scoring.hint-penalty}")
            double hintPenalty,

            @Value("${gitgud.quiz-scoring.time-penalty}")
            double timePenalty,

            @Value("${gitgud.quiz-scoring.free-time-seconds}")
            int freeTimeSeconds,

            @Value("${gitgud.quiz-scoring.skip-penalty}")
            int skipPenalty
    ) {

        this.quizAttemptService =
                quizAttemptService;

        this.questionAttemptService =
                questionAttemptService;

        this.questionRepository =
                questionRepository;

        this.properties =
                properties;

        this.basePoints =
                basePoints;

        this.hintPenalty =
                hintPenalty;

        this.timePenalty =
                timePenalty;

        this.freeTimeSeconds =
                freeTimeSeconds;

        this.skipPenalty =
                skipPenalty;
    }

    public QuizResult getResult(
            String userId,
            String attemptId
    ) throws Exception {

        QuizAttempt attempt =
                quizAttemptService.getAttempt(
                        userId,
                        attemptId
                );

        if (
                attempt.getStatus()
                        == QuizAttemptStatus.IN_PROGRESS
        ) {

            throw new IllegalStateException(
                    "Quiz attempt is still in progress"
            );
        }

        return calculateResult(
                userId,
                attempt
        );
    }

    public QuizResult calculateResultForFinalization(
            String userId,
            QuizAttempt attempt
    ) throws Exception {

        if (
                attempt.getStatus()
                        != QuizAttemptStatus.IN_PROGRESS
        ) {

            throw new IllegalStateException(
                    "Quiz attempt is not in progress"
            );
        }

        return calculateResult(
                userId,
                attempt
        );
    }

    private QuizResult calculateResult(
            String userId,
            QuizAttempt attempt
    ) throws Exception {

        if (
                attempt.getStatus()
                        == QuizAttemptStatus.CANCELLED
        ) {

            return new QuizResult(
                    0,
                    QuizGrade.FAILURE,
                    0
            );
        }

        Quiz quiz =
                quizAttemptService.getQuiz(
                        attempt
                );

        List<QuestionAttempt> questionAttempts =
                questionAttemptService
                        .getQuestionAttempts(
                                userId,
                                attempt.getId()
                        );

        validateCompletedAttempt(
                questionAttempts
        );

        return calculateResult(
                quiz,
                questionAttempts
        );
    }

    private void validateCompletedAttempt(
            List<QuestionAttempt> questionAttempts
    ) {

        int requiredQuestionCount =
                properties
                        .getQuiz()
                        .getQuestionsPerAttempt();

        if (
                requiredQuestionCount <= 0
        ) {

            throw new IllegalStateException(
                    "Questions per quiz must be greater than zero"
            );
        }

        if (
                questionAttempts.size()
                        != requiredQuestionCount
        ) {

            throw new IllegalStateException(
                    "Completed quiz must contain exactly "
                            + requiredQuestionCount
                            + " question attempts"
            );
        }

        Set<String> questionIds =
                new HashSet<>();

        for (
                QuestionAttempt attempt
                : questionAttempts
        ) {

            if (
                    !questionIds.add(
                            attempt.getQuestionId()
                    )
            ) {

                throw new IllegalStateException(
                        "Completed quiz cannot contain duplicate question attempts"
                );
            }
        }
    }

    private QuizResult calculateResult(
            Quiz quiz,
            List<QuestionAttempt> questionAttempts
    ) throws Exception {

        int correctCount = 0;
        int score = 0;
        int totalSkips = 0;

        for (
                QuestionAttempt attempt
                : questionAttempts
        ) {

            Question question =
                    questionRepository.findQuestion(
                            quiz,
                            attempt.getQuestionId()
                    );

            if (question == null) {

                throw new IllegalStateException(
                        "Question does not belong to quiz"
                );
            }

            totalSkips +=
                    Math.max(
                            0,
                            attempt.getSkipCount()
                    );

            if (attempt.isCorrect()) {
                correctCount++;
            }

            score +=
                    calculateQuestionScore(
                            question,
                            attempt
                    );
        }

        score -=
                totalSkips * skipPenalty;

        score =
                Math.max(
                        0,
                        score
                );

        QuizGrade grade =
                calculateGrade(
                        correctCount
                );

        return new QuizResult(
                score,
                grade,
                correctCount
        );
    }

    private int calculateQuestionScore(
            Question question,
            QuestionAttempt attempt
    ) {

        if (!attempt.isCorrect()) {
            return 0;
        }

        int questionPoints =
                basePoints
                        * getDifficultyMultiplier(
                        question.getDifficulty()
                );

        double hintReduction =
                questionPoints
                        * hintPenalty
                        * Math.max(
                        0,
                        attempt.getHintsUsed()
                );

        double extraSeconds =
                Math.max(
                        0,
                        (
                                attempt.getTimeSpentMillis()
                                        / 1000.0
                        ) - freeTimeSeconds
                );

        double timeReduction =
                questionPoints
                        * timePenalty
                        * extraSeconds;

        double score =
                questionPoints
                        - hintReduction
                        - timeReduction;

        return Math.max(
                0,
                (int) Math.round(score)
        );
    }

    private int getDifficultyMultiplier(
            QuestionDifficulty difficulty
    ) {

        return switch (difficulty) {

            case EASY -> 2;
            case MEDIUM -> 3;
            case HARD -> 4;
        };
    }

    private QuizGrade calculateGrade(
            int correctCount
    ) {

        return switch (correctCount) {

            case 5 -> QuizGrade.PERFECT;
            case 4 -> QuizGrade.EXCELLENT;
            case 3 -> QuizGrade.PASSING;
            default -> QuizGrade.FAILURE;
        };
    }
}
