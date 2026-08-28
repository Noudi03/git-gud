package com.app.gitgud.model;

public class QuizDetails {

    private Quiz quiz;
    private QuizContext context;

    public QuizDetails(
            Quiz quiz,
            QuizContext context
    ) {
        this.quiz = quiz;
        this.context = context;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public QuizContext getContext() {
        return context;
    }
}
