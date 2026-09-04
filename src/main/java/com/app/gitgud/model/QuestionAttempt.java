package com.app.gitgud.model;

public class QuestionAttempt {

    private final String id;
    private final String questionId;
    private final boolean correct;
    private final long timeSpentMillis;
    private final int hintsUsed;
    private final int skipCount;

    public QuestionAttempt(
            String id,
            String questionId,
            boolean correct,
            long timeSpentMillis,
            int hintsUsed,
            int skipCount
    ) {
        this.id = id;
        this.questionId = questionId;
        this.correct = correct;
        this.timeSpentMillis = timeSpentMillis;
        this.hintsUsed = hintsUsed;
        this.skipCount = skipCount;
    }

    public String getId() {
        return id;
    }

    public String getQuestionId() {
        return questionId;
    }

    public boolean isCorrect() {
        return correct;
    }

    public long getTimeSpentMillis() {
        return timeSpentMillis;
    }

    public int getHintsUsed() {
        return hintsUsed;
    }

    public int getSkipCount() {
        return skipCount;
    }
}
