package com.app.gitgud.model;

import java.util.List;

public class Quiz {
    private String id;
    private String chapterId;
    private String sectionId;
    private String type;
    private List<String> questionIds;

    public Quiz(){}

    public Quiz(String id, String chapterId, String sectionId, String type, List<String> questions) {
        this.id = id;
        this.chapterId = chapterId;
        this.sectionId = sectionId;
        this.type = type;
        this.questionIds = questionIds;
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

    public List<String> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<String> questionIds) {
        this.questionIds = questionIds;
    }
}
