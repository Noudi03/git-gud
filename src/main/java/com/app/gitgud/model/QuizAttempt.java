package com.app.gitgud.model;

public class QuizAttempt {

    private final String id;
    private final String quizId;
    private final QuizAttemptStatus status;
    private final QuizContext context;
    private final boolean outcomeProcessed;


    public QuizAttempt(
            String id,
            String quizId,
            QuizAttemptStatus status,
            QuizContext context
    ) {

        this(
                id,
                quizId,
                status,
                context,
                false
        );
    }


    public QuizAttempt(
            String id,
            String quizId,
            QuizAttemptStatus status,
            QuizContext context,
            boolean outcomeProcessed
    ) {

        this.id = id;
        this.quizId = quizId;
        this.status = status;
        this.context = context;
        this.outcomeProcessed = outcomeProcessed;
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


    public boolean isOutcomeProcessed() {
        return outcomeProcessed;
    }
}
