package com.app.gitgud.model;

public class SectionQuizContext implements QuizContext {

    private String chapterId;
    private String sectionId;

    public SectionQuizContext() {
    }

    public SectionQuizContext(
            String chapterId,
            String sectionId
    ) {
        this.chapterId = chapterId;
        this.sectionId = sectionId;
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
