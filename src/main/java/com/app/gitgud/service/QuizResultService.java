package com.app.gitgud.service;

import com.app.gitgud.model.ChapterQuizContext;
import com.app.gitgud.model.GlobalQuizContext;
import com.app.gitgud.model.Question;
import com.app.gitgud.model.QuestionAttempt;
import com.app.gitgud.model.QuestionDifficulty;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizAttemptStatus;
import com.app.gitgud.model.QuizContext;
import com.app.gitgud.model.QuizGrade;
import com.app.gitgud.model.QuizResult;
import com.app.gitgud.model.SectionQuizContext;
import com.app.gitgud.repository.FirestoreQuestionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizResultService {

    private final QuizAttemptService quizAttemptService;
    private final QuestionAttemptService questionAttemptService;
    private final QuizService quizService;
    private final FirestoreQuestionRepository questionRepository;

    private final int basePoints;
    private final double hintPenalty;
    private final double timePenalty;
    private final int freeTimeSeconds;
    private final int skipPenalty;

    public QuizResultService(
            QuizAttemptService quizAttemptService,
            QuestionAttemptService questionAttemptService,
            QuizService quizService,
            FirestoreQuestionRepository questionRepository,

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

        this.quizService =
                quizService;

        this.questionRepository =
                questionRepository;

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
                        == QuizAttemptStatus.CANCELLED
        ) {
            return new QuizResult(
                    0,
                    QuizGrade.FAILURE,
                    0
            );
        }

        Quiz quiz =
                getQuiz(attempt);

        List<QuestionAttempt> questionAttempts =
                questionAttemptService
                        .getQuestionAttempts(
                                userId,
                                attemptId
                        );

        return calculateResult(
                quiz,
                questionAttempts
        );
    }

    private QuizResult calculateResult(
            Quiz quiz,
            List<QuestionAttempt> questionAttempts
    ) throws Exception {

        int correctCount =
                0;

        int score =
                0;

        int totalSkips =
                0;

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
                continue;
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

            case EASY ->
                    2;

            case MEDIUM ->
                    3;

            case HARD ->
                    4;
        };
    }

    private QuizGrade calculateGrade(
            int correctCount
    ) {

        return switch (correctCount) {

            case 5 ->
                    QuizGrade.PERFECT;

            case 4 ->
                    QuizGrade.EXCELLENT;

            case 3 ->
                    QuizGrade.PASSING;

            default ->
                    QuizGrade.FAILURE;
        };
    }

    private Quiz getQuiz(
            QuizAttempt attempt
    ) throws Exception {

        QuizContext context =
                attempt.getContext();

        if (context instanceof SectionQuizContext sectionContext) {

            return quizService.getSectionQuiz(
                    sectionContext.getChapterId(),
                    sectionContext.getSectionId(),
                    attempt.getQuizId()
            );
        }

        if (context instanceof ChapterQuizContext chapterContext) {

            return quizService.getChapterQuiz(
                    chapterContext.getChapterId(),
                    attempt.getQuizId()
            );
        }

        if (context instanceof GlobalQuizContext) {

            return quizService.getGlobalQuiz(
                    attempt.getQuizId()
            );
        }

        throw new IllegalArgumentException(
                "Unsupported quiz context: "
                        + context.getClass().getName()
        );
    }
}
