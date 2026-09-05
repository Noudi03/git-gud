package com.app.gitgud.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "gitgud")
public class GitGudProperties {

    private Quiz quiz = new Quiz();
    private Rating rating = new Rating();

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
}
