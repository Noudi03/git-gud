package com.app.gitgud.model;

import java.util.Map;

public class Question {
    private String id;
    private String chapterId;
    private String sectionId;
    private String text;
    private Map<String, String> options;
    private String correctAnswer;
    private String hint;

    public Question() {}

    public Question(String id, String chapterId, String sectionId, String text, Map<String, String> options, String correctAnswer, String hint){
        this.id = id;
        this.chapterId = chapterId;
        this.sectionId = sectionId;
        this.text = text;
        this.options = options;
        this.correctAnswer= correctAnswer;
        this.hint =hint;
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

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
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
}
