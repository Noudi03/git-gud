package com.app.gitgud.model;

public class ChapterQuizContext
        implements QuizContext {

    private final String chapterId;


    public ChapterQuizContext(
            String chapterId
    ) {

        this.chapterId = chapterId;
    }


    public String getChapterId() {
        return chapterId;
    }
}
