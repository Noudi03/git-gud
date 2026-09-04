package com.app.gitgud.model;

public class QuizAttempt {

    private final String id;
    private final String quizId;
    private final QuizAttemptStatus status;
    private final QuizContext context;

    public QuizAttempt(
            String id,
            String quizId,
            QuizAttemptStatus status,
            QuizContext context
    ) {
        this.id = id;
        this.quizId = quizId;
        this.status = status;
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public String getQuizId() {
        return quizId;
    }

    public QuizAttemptStatus getStatus() {
        return status;
    }

    public QuizContext getContext() {
        return context;
    }
}
