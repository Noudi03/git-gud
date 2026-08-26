package com.app.gitgud.model;

import java.util.Map;

public class Question {
    private String id;
    private String chapterId;
    private String sectionId;
    private String text;
    private Map<String, String> options;
    private String correctAnswer;
    private String difficulty;
    private String hint1;
    private String hint2;

    public Question() {}

    public Question(String id, String chapterId, String sectionId, String text, Map<String, String> options, String correctAnswer, String difficulty, String hint1, String hint2) {
        this.id = id;
        this.chapterId = chapterId;
        this.sectionId = sectionId;
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
        this.hint1 = hint1;
        this.hint2 = hint2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setHint1(String hint1) {
        this.hint1 = hint1;
    }

    public String getHint1() {
        return hint1;
    }

    public String getHint2() {
        return hint2;
    }

    public void setHint2(String hint2) {
        this.hint2 = hint2;
    }
}
