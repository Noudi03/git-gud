package com.app.gitgud.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "gitgud")
public class GitGudProperties {

    private Quiz quiz = new Quiz();
    private Rating rating = new Rating();
    private QuestionSelection questionSelection =
            new QuestionSelection();
    private QuizScoring quizScoring =
            new QuizScoring();

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(
            Quiz quiz
    ) {
        this.quiz = quiz;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(
            Rating rating
    ) {
        this.rating = rating;
    }

    public QuestionSelection getQuestionSelection() {
        return questionSelection;
    }

    public void setQuestionSelection(
            QuestionSelection questionSelection
    ) {
        this.questionSelection =
                questionSelection;
    }

    public QuizScoring getQuizScoring() {
        return quizScoring;
    }

    public void setQuizScoring(
            QuizScoring quizScoring
    ) {
        this.quizScoring =
                quizScoring;
    }

    public static class Quiz {

        private int questionsPerAttempt;

        public int getQuestionsPerAttempt() {
            return questionsPerAttempt;
        }

        public void setQuestionsPerAttempt(
                int questionsPerAttempt
        ) {
            this.questionsPerAttempt =
                    questionsPerAttempt;
        }
    }

    public static class Rating {

        private int defaultRating;
        private int min;
        private int experiencedThreshold;
        private int kFactor;

        public int getDefaultRating() {
            return defaultRating;
        }

        public void setDefaultRating(
                int defaultRating
        ) {
            this.defaultRating =
                    defaultRating;
        }

        public int getMin() {
            return min;
        }

        public void setMin(
                int min
        ) {
            this.min =
                    min;
        }

        public int getExperiencedThreshold() {
            return experiencedThreshold;
        }

        public void setExperiencedThreshold(
                int experiencedThreshold
        ) {
            this.experiencedThreshold =
                    experiencedThreshold;
        }

        public int getKFactor() {
            return kFactor;
        }

        public void setKFactor(
                int kFactor
        ) {
            this.kFactor =
                    kFactor;
        }
    }

    public static class QuestionSelection {

        private DifficultyWeights novice =
                new DifficultyWeights();

        private DifficultyWeights experienced =
                new DifficultyWeights();

        public DifficultyWeights getNovice() {
            return novice;
        }

        public void setNovice(
                DifficultyWeights novice
        ) {
            this.novice = novice;
        }

        public DifficultyWeights getExperienced() {
            return experienced;
        }

        public void setExperienced(
                DifficultyWeights experienced
        ) {
            this.experienced = experienced;
        }
    }

    public static class DifficultyWeights {

        private int easy;
        private int medium;
        private int hard;

        public int getEasy() {
            return easy;
        }

        public void setEasy(
                int easy
        ) {
            this.easy = easy;
        }

        public int getMedium() {
            return medium;
        }

        public void setMedium(
                int medium
        ) {
            this.medium = medium;
        }

        public int getHard() {
            return hard;
        }

        public void setHard(
                int hard
        ) {
            this.hard = hard;
        }
    }

    public static class QuizScoring {

        private int basePoints;
        private double hintPenalty;
        private double timePenalty;
        private int freeTimeSeconds;
        private int skipPenalty;

        public int getBasePoints() {
            return basePoints;
        }

        public void setBasePoints(
                int basePoints
        ) {
            this.basePoints = basePoints;
        }

        public double getHintPenalty() {
            return hintPenalty;
        }

        public void setHintPenalty(
                double hintPenalty
        ) {
            this.hintPenalty = hintPenalty;
        }

        public double getTimePenalty() {
            return timePenalty;
        }

        public void setTimePenalty(
                double timePenalty
        ) {
            this.timePenalty = timePenalty;
        }

        public int getFreeTimeSeconds() {
            return freeTimeSeconds;
        }

        public void setFreeTimeSeconds(
                int freeTimeSeconds
        ) {
            this.freeTimeSeconds =
                    freeTimeSeconds;
        }

        public int getSkipPenalty() {
            return skipPenalty;
        }

        public void setSkipPenalty(
                int skipPenalty
        ) {
            this.skipPenalty = skipPenalty;
        }
    }
}
