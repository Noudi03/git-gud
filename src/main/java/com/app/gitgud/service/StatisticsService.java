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
import com.app.gitgud.model.dto.StatisticsDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    private final QuizAttemptService quizAttemptService;
    private final QuestionAttemptService questionAttemptService;
    private final QuizResultService quizResultService;
    private final FirestoreQuestionRepository questionRepository;

    public StatisticsService(
            QuizAttemptService quizAttemptService,
            QuestionAttemptService questionAttemptService,
            QuizResultService quizResultService,
            FirestoreQuestionRepository questionRepository
    ) {
        this.quizAttemptService =
                quizAttemptService;

        this.questionAttemptService =
                questionAttemptService;

        this.quizResultService =
                quizResultService;

        this.questionRepository =
                questionRepository;
    }

    public StatisticsDto getStatistics(
            String userId,
            String selectedQuizKey
    ) throws Exception {

        List<QuizAttempt> attempts =
                quizAttemptService.getAttempts(
                        userId
                );

        List<QuizAttempt> completedAttempts =
                new ArrayList<>();

        for (QuizAttempt attempt : attempts) {

            if (
                    attempt.getStatus()
                            != QuizAttemptStatus.COMPLETED
            ) {
                continue;
            }

            if (
                    selectedQuizKey == null
                            || selectedQuizKey.isBlank()
            ) {
                completedAttempts.add(
                        attempt
                );
                continue;
            }

            Quiz quiz =
                    quizAttemptService.getQuiz(
                            attempt
                    );

            if (
                    selectedQuizKey.equals(
                            createQuizKey(quiz)
                    )
            ) {
                completedAttempts.add(
                        attempt
                );
            }
        }

        return buildStatistics(
                userId,
                completedAttempts
        );
    }

    private StatisticsDto buildStatistics(
            String userId,
            List<QuizAttempt> attempts
    ) throws Exception {

        int totalCorrect = 0;
        int totalIncorrect = 0;
        int totalSkipped = 0;
        int totalHintsUsed = 0;
        int totalQuestions = 0;
        long totalTimeSpentMillis = 0;
        int totalScore = 0;

        List<StatisticsDto.PerformancePoint> performance =
                new ArrayList<>();

        Map<String, QuizAggregate> quizAggregates =
                new LinkedHashMap<>();

        Map<QuestionDifficulty, DifficultyAggregate>
                difficultyAggregates =
                new EnumMap<>(
                        QuestionDifficulty.class
                );

        Map<String, Integer> grades =
                new LinkedHashMap<>();

        int attemptNumber = 0;

        for (QuizAttempt attempt : attempts) {

            attemptNumber++;

            Quiz quiz =
                    quizAttemptService.getQuiz(
                            attempt
                    );

            List<QuestionAttempt> questionAttempts =
                    questionAttemptService.getQuestionAttempts(
                            userId,
                            attempt.getId()
                    );

            QuizResult result =
                    quizResultService.getResult(
                            userId,
                            attempt.getId()
                    );

            int score =
                    result.getScore();

            totalScore += score;

            totalCorrect +=
                    result.getCorrectCount();

            performance.add(
                    new StatisticsDto.PerformancePoint(
                            attemptNumber,
                            score
                    )
            );

            String quizKey =
                    createQuizKey(
                            quiz
                    );

            QuizAggregate quizAggregate =
                    quizAggregates.computeIfAbsent(
                            quizKey,
                            id ->
                                    new QuizAggregate(
                                            quiz
                                    )
                    );

            quizAggregate.attempts++;
            quizAggregate.totalScore += score;

            QuizGrade grade =
                    result.getGrade();

            grades.merge(
                    grade.getValue(),
                    1,
                    Integer::sum
            );

            for (
                    QuestionAttempt questionAttempt
                    : questionAttempts
            ) {

                totalQuestions++;

                totalHintsUsed +=
                        Math.max(
                                0,
                                questionAttempt.getHintsUsed()
                        );

                totalTimeSpentMillis +=
                        Math.max(
                                0,
                                questionAttempt.getTimeSpentMillis()
                        );

                Question question =
                        questionRepository.findQuestion(
                                quiz,
                                questionAttempt.getQuestionId()
                        );

                if (question == null) {
                    continue;
                }

                if (
                        !questionAttempt.isCorrect()
                                && questionAttempt.getSkipCount() > 0
                ) {

                    totalSkipped++;

                } else if (
                        !questionAttempt.isCorrect()
                ) {

                    totalIncorrect++;
                }

                DifficultyAggregate difficultyAggregate =
                        difficultyAggregates.computeIfAbsent(
                                question.getDifficulty(),
                                key ->
                                        new DifficultyAggregate()
                        );

                difficultyAggregate.total++;

                if (questionAttempt.isCorrect()) {
                    difficultyAggregate.correct++;
                }
            }
        }

        int averageScore =
                attempts.isEmpty()
                        ? 0
                        : Math.round(
                        (float) totalScore
                                / attempts.size()
                );

        int averageAccuracy =
                totalQuestions == 0
                        ? 0
                        : Math.round(
                        (float) totalCorrect
                                / totalQuestions
                                * 100
                );

        List<StatisticsDto.QuizSummary> quizzes =
                quizAggregates.values()
                        .stream()
                        .map(
                                aggregate ->
                                        new StatisticsDto.QuizSummary(
                                                createQuizKey(
                                                        aggregate.quiz
                                                ),
                                                aggregate.quiz.getId(),
                                                aggregate.quiz.getTitle(),
                                                aggregate.quiz.getType() == null
                                                        ? null
                                                        : aggregate.quiz
                                                        .getType()
                                                        .getValue(),
                                                createQuizContextDto(
                                                        aggregate.quiz.getContext()
                                                ),
                                                aggregate.attempts,
                                                aggregate.attempts == 0
                                                        ? 0
                                                        : Math.round(
                                                        (float)
                                                                aggregate.totalScore
                                                                / aggregate.attempts
                                                )
                                        )
                        )
                        .sorted(
                                Comparator.comparing(
                                        StatisticsDto.QuizSummary::getTitle
                                )
                        )
                        .toList();

        List<StatisticsDto.DifficultyStatistics>
                difficulty =
                List.of(
                        createDifficultyStatistics(
                                QuestionDifficulty.EASY,
                                difficultyAggregates
                        ),
                        createDifficultyStatistics(
                                QuestionDifficulty.MEDIUM,
                                difficultyAggregates
                        ),
                        createDifficultyStatistics(
                                QuestionDifficulty.HARD,
                                difficultyAggregates
                        )
                );

        StatisticsDto.Summary summary =
                new StatisticsDto.Summary(
                        attempts.size(),
                        averageScore,
                        averageAccuracy,
                        totalCorrect,
                        totalIncorrect,
                        totalSkipped,
                        totalHintsUsed,
                        totalTimeSpentMillis
                );

        return new StatisticsDto(
                summary,
                quizzes,
                performance,
                new StatisticsDto.AnswerBreakdown(
                        totalCorrect,
                        totalIncorrect,
                        totalSkipped
                ),
                difficulty,
                grades
        );
    }

    private String createQuizKey(
            Quiz quiz
    ) {

        QuizContext context =
                quiz.getContext();

        if (
                context instanceof SectionQuizContext
                        sectionContext
        ) {

            return "section:"
                    + sectionContext.getChapterId()
                    + ":"
                    + sectionContext.getSectionId()
                    + ":"
                    + quiz.getId();
        }

        if (
                context instanceof ChapterQuizContext
                        chapterContext
        ) {

            return "chapter:"
                    + chapterContext.getChapterId()
                    + ":"
                    + quiz.getId();
        }

        if (
                context instanceof GlobalQuizContext
        ) {

            return "global:"
                    + quiz.getId();
        }

        throw new IllegalArgumentException(
                "Unsupported quiz context: "
                        + context.getClass().getName()
        );
    }

    private StatisticsDto.QuizContextDto
    createQuizContextDto(
            QuizContext context
    ) {

        if (
                context instanceof SectionQuizContext
                        sectionContext
        ) {

            return new StatisticsDto.QuizContextDto(
                    "section",
                    sectionContext.getChapterId(),
                    sectionContext.getSectionId()
            );
        }

        if (
                context instanceof ChapterQuizContext
                        chapterContext
        ) {

            return new StatisticsDto.QuizContextDto(
                    "chapter",
                    chapterContext.getChapterId(),
                    null
            );
        }

        if (
                context instanceof GlobalQuizContext
        ) {

            return new StatisticsDto.QuizContextDto(
                    "global",
                    null,
                    null
            );
        }

        throw new IllegalArgumentException(
                "Unsupported quiz context: "
                        + context.getClass().getName()
        );
    }

    private StatisticsDto.DifficultyStatistics
    createDifficultyStatistics(
            QuestionDifficulty difficulty,
            Map<QuestionDifficulty, DifficultyAggregate>
                    aggregates
    ) {

        DifficultyAggregate aggregate =
                aggregates.get(
                        difficulty
                );

        if (aggregate == null) {

            return new StatisticsDto.DifficultyStatistics(
                    difficulty.name(),
                    0,
                    0,
                    0
            );
        }

        int percentage =
                aggregate.total == 0
                        ? 0
                        : Math.round(
                        (float) aggregate.correct
                                / aggregate.total
                                * 100
                );

        return new StatisticsDto.DifficultyStatistics(
                difficulty.name(),
                aggregate.correct,
                aggregate.total,
                percentage
        );
    }

    private static class QuizAggregate {

        private final Quiz quiz;

        private int attempts;
        private int totalScore;

        private QuizAggregate(
                Quiz quiz
        ) {
            this.quiz = quiz;
        }
    }

    private static class DifficultyAggregate {

        private int correct;
        private int total;
    }
}
