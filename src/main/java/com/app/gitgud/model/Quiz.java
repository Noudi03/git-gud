package com.app.gitgud.model;

import java.util.List;

public class Quiz {
    private String id;
    private String chapterId;
    private String sectionId;
    private String type;
    private List<Question> questions;

    public Quiz(){}

    public Quiz(String id, String chapterId, String sectionId, String type, List<Question> questions) {
        this.id = id;
        this.chapterId = chapterId;
        this.sectionId = sectionId;
        this.type = type;
        this.questions = questions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
