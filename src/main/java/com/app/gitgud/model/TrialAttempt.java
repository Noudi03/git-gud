package com.app.gitgud.model;

public class TrialAttempt {

    private final String trialId;
    private final boolean correct;
    private final long timeSpentMillis;
    private final int hintsUsed;
    private final int skipCount;

    public TrialAttempt(
            String trialId,
            boolean correct,
            long timeSpentMillis,
            int hintsUsed,
            int skipCount
    ) {
        this.trialId = trialId;
        this.correct = correct;
        this.timeSpentMillis = timeSpentMillis;
        this.hintsUsed = hintsUsed;
        this.skipCount = skipCount;
    }

    public String getTrialId() {
        return trialId;
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
