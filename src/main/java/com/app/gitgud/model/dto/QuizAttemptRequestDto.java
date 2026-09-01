package com.app.gitgud.model.dto;

import java.util.List;

public class QuizAttemptRequestDto {

    private String quizId;
    private String status;
    private List<QuestionAttemptRequestDto> questionAttempts;


    public QuizAttemptRequestDto() {
    }


    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(
            String quizId
    ) {

        this.quizId =
                quizId;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(
            String status
    ) {

        this.status =
                status;
    }


    public List<QuestionAttemptRequestDto>
    getQuestionAttempts() {

        return questionAttempts;
    }

    public void setQuestionAttempts(
            List<QuestionAttemptRequestDto>
                    questionAttempts
    ) {

        this.questionAttempts =
                questionAttempts;
    }
}
