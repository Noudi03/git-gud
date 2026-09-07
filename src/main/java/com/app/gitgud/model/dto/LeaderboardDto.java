package com.app.gitgud.model.dto;

import java.util.List;

public class LeaderboardDto {

    private final List<QuizSummary> quizzes;
    private final List<Entry> entries;

    public LeaderboardDto(
            List<QuizSummary> quizzes,
            List<Entry> entries
    ) {
        this.quizzes = quizzes;
        this.entries = entries;
    }

    public List<QuizSummary> getQuizzes() {
        return quizzes;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public static class QuizSummary {

        private final String key;
        private final String quizId;
        private final String title;
        private final String type;
        private final QuizContextDto context;

        public QuizSummary(
                String key,
                String quizId,
                String title,
                String type,
                QuizContextDto context
        ) {
            this.key = key;
            this.quizId = quizId;
            this.title = title;
            this.type = type;
            this.context = context;
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

    public static class Entry {

        private final int rank;
        private final String username;
        private final int score;

        public Entry(
                int rank,
                String username,
                int score
        ) {
            this.rank = rank;
            this.username = username;
            this.score = score;
        }

        public int getRank() {
            return rank;
        }

        public String getUsername() {
            return username;
        }

        public int getScore() {
            return score;
        }
    }
}
