package com.app.gitgud.model;

public class ChapterQuizContext implements QuizContext {

    private String chapterId;

    public ChapterQuizContext() {
    }

    public ChapterQuizContext(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }
}
