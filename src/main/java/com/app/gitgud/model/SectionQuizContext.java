package com.app.gitgud.model;

public class SectionQuizContext
        implements QuizContext {

    private final String chapterId;
    private final String sectionId;


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


    public String getSectionId() {
        return sectionId;
    }
}
