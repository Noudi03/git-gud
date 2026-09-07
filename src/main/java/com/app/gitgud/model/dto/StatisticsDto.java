package com.app.gitgud.model.dto;

import java.util.List;
import java.util.Map;

public class StatisticsDto {

    private final Summary summary;
    private final List<QuizSummary> quizzes;
    private final List<PerformancePoint> performance;
    private final AnswerBreakdown answerBreakdown;
    private final List<DifficultyStatistics> difficulty;
    private final Map<String, Integer> grades;

    public StatisticsDto(
            Summary summary,
            List<QuizSummary> quizzes,
            List<PerformancePoint> performance,
            AnswerBreakdown answerBreakdown,
            List<DifficultyStatistics> difficulty,
            Map<String, Integer> grades
    ) {
        this.summary = summary;
        this.quizzes = quizzes;
        this.performance = performance;
        this.answerBreakdown = answerBreakdown;
        this.difficulty = difficulty;
        this.grades = grades;
    }

    public Summary getSummary() {
        return summary;
    }

    public List<QuizSummary> getQuizzes() {
        return quizzes;
    }

    public List<PerformancePoint> getPerformance() {
        return performance;
    }

    public AnswerBreakdown getAnswerBreakdown() {
        return answerBreakdown;
    }

    public List<DifficultyStatistics> getDifficulty() {
        return difficulty;
    }

    public Map<String, Integer> getGrades() {
        return grades;
    }

    public static class Summary {

        private final int completedAttempts;
        private final int averageScore;
        private final int averageAccuracy;
        private final int totalCorrect;
        private final int totalIncorrect;
        private final int totalSkipped;
        private final int totalHintsUsed;
        private final long totalTimeSpentMillis;

        public Summary(
                int completedAttempts,
                int averageScore,
                int averageAccuracy,
                int totalCorrect,
                int totalIncorrect,
                int totalSkipped,
                int totalHintsUsed,
                long totalTimeSpentMillis
        ) {
            this.completedAttempts = completedAttempts;
            this.averageScore = averageScore;
            this.averageAccuracy = averageAccuracy;
            this.totalCorrect = totalCorrect;
            this.totalIncorrect = totalIncorrect;
            this.totalSkipped = totalSkipped;
            this.totalHintsUsed = totalHintsUsed;
            this.totalTimeSpentMillis = totalTimeSpentMillis;
        }

        public int getCompletedAttempts() {
            return completedAttempts;
        }

        public int getAverageScore() {
            return averageScore;
        }

        public int getAverageAccuracy() {
            return averageAccuracy;
        }

        public int getTotalCorrect() {
            return totalCorrect;
        }

        public int getTotalIncorrect() {
            return totalIncorrect;
        }

        public int getTotalSkipped() {
            return totalSkipped;
        }

        public int getTotalHintsUsed() {
            return totalHintsUsed;
        }

        public long getTotalTimeSpentMillis() {
            return totalTimeSpentMillis;
        }
    }

    public static class QuizSummary {

        private final String key;
        private final String quizId;
        private final String title;
        private final String type;
        private final QuizContextDto context;
        private final int attempts;
        private final int averageScore;

        public QuizSummary(
                String key,
                String quizId,
                String title,
                String type,
                QuizContextDto context,
                int attempts,
                int averageScore
        ) {
            this.key = key;
            this.quizId = quizId;
            this.title = title;
            this.type = type;
            this.context = context;
            this.attempts = attempts;
            this.averageScore = averageScore;
        }

        public String getKey() {
            return key;
        }

        public String getQuizId() {
            return quizId;
        }

        public String getTitle() {
            return title;
        }

        public String getType() {
            return type;
        }

        public QuizContextDto getContext() {
            return context;
        }

        public int getAttempts() {
            return attempts;
        }

        public int getAverageScore() {
            return averageScore;
        }
    }

    public static class QuizContextDto {

        private final String type;
        private final String chapterId;
        private final String sectionId;

        public QuizContextDto(
                String type,
                String chapterId,
                String sectionId
        ) {
            this.type = type;
            this.chapterId = chapterId;
            this.sectionId = sectionId;
        }

        public String getType() {
            return type;
        }

        public String getChapterId() {
            return chapterId;
        }

        public String getSectionId() {
            return sectionId;
        }
    }

    public static class PerformancePoint {

        private final int attempt;
        private final int score;

        public PerformancePoint(
                int attempt,
                int score
        ) {
            this.attempt = attempt;
            this.score = score;
        }

        public int getAttempt() {
            return attempt;
        }

        public int getScore() {
            return score;
        }
    }

    public static class AnswerBreakdown {

        private final int correct;
        private final int incorrect;
        private final int skipped;

        public AnswerBreakdown(
                int correct,
                int incorrect,
                int skipped
        ) {
            this.correct = correct;
            this.incorrect = incorrect;
            this.skipped = skipped;
        }

        public int getCorrect() {
            return correct;
        }

        public int getIncorrect() {
            return incorrect;
        }

        public int getSkipped() {
            return skipped;
        }
    }

    public static class DifficultyStatistics {

        private final String difficulty;
        private final int correct;
        private final int total;
        private final int percentage;

        public DifficultyStatistics(
                String difficulty,
                int correct,
                int total,
                int percentage
        ) {
            this.difficulty = difficulty;
            this.correct = correct;
            this.total = total;
            this.percentage = percentage;
        }

        public String getDifficulty() {
            return difficulty;
        }

        public int getCorrect() {
            return correct;
        }

        public int getTotal() {
            return total;
        }

        public int getPercentage() {
            return percentage;
        }
    }
}
