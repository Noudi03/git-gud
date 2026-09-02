package com.app.gitgud.model.dto;

public class TrialAttemptRequestDto {

    private String trialId;
    private boolean correct;
    private long timeSpentMillis;
    private int hintsUsed;
    private int skipCount;

    public TrialAttemptRequestDto() {
    }

    public String getTrialId() {
        return trialId;
    }

    public void setTrialId(
            String trialId
    ) {
        this.trialId = trialId;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(
            boolean correct
    ) {
        this.correct = correct;
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
