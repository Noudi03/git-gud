package com.app.gitgud.model.dto;

public class QuizAttemptRequestDto {

    private String quizId;
    private String status;

    public QuizAttemptRequestDto() {
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(
            String quizId
    ) {
        this.quizId = quizId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(
            String status
    ) {
        this.status = status;
    }
}
