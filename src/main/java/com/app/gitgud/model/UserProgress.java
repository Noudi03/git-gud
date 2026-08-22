package com.app.gitgud.model;

import java.util.ArrayList;
import java.util.List;

public class UserProgress {
    private String userId;
    private String chapterId;
    private boolean completed;
    private int score;
    private int attempts;
    private long timeSpent;
    private boolean unlockedHardQuestion;
    private List<String> reviewNeeded;

    public UserProgress() {}

    public UserProgress(String userId, String chapterId) {
        this.userId = userId;
        this.chapterId = chapterId;
        this.completed = false;
        this.score = 0;
        this.attempts = 0;
        this.timeSpent = 0;
        this.unlockedHardQuestion = false;
        this.reviewNeeded = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent;
    }

    public boolean isUnlockedHardQuestion() {
        return unlockedHardQuestion;
    }

    public void setUnlockedHardQuestion(boolean unlockedHardQuestion) {
        this.unlockedHardQuestion = unlockedHardQuestion;
    }

    public List<String> getReviewNeeded() {
        return reviewNeeded;
    }

    public void setReviewNeeded(List<String> reviewNeeded) {
        this.reviewNeeded = reviewNeeded;
    }
}
