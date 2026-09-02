package com.app.gitgud.model;

public class QuizAttempt {

    private final String id;
    private final String quizId;
    private final QuizAttemptStatus status;

    public QuizAttempt(
            String id,
            String quizId,
            QuizAttemptStatus status
    ) {
        this.id = id;
        this.quizId = quizId;
        this.status = status;
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
}
