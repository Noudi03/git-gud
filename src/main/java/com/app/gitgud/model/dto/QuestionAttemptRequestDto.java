package com.app.gitgud.model.dto;

public class QuestionAttemptRequestDto {

    private String questionId;
    private Object selectedAnswer;
    private long timeSpentMillis;
    private int hintsUsed;
    private int skipCount;

    public QuestionAttemptRequestDto() {
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(
            String questionId
    ) {
        this.questionId = questionId;
    }

    public Object getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(
            Object selectedAnswer
    ) {
        this.selectedAnswer = selectedAnswer;
    }

    public long getTimeSpentMillis() {
        return timeSpentMillis;
    }

    public void setTimeSpentMillis(
            long timeSpentMillis
    ) {
        this.timeSpentMillis = timeSpentMillis;
    }

    public int getHintsUsed() {
        return hintsUsed;
    }

    public void setHintsUsed(
            int hintsUsed
    ) {
        this.hintsUsed = hintsUsed;
    }

    public int getSkipCount() {
        return skipCount;
    }

    public void setSkipCount(
            int skipCount
    ) {
        this.skipCount = skipCount;
    }
}
