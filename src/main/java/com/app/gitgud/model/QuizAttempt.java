package com.app.gitgud.model;

import java.util.List;

public class QuizAttempt {

    private final String id;
    private final String quizId;
    private final QuizAttemptStatus status;
    private final List<QuestionAttempt> questionAttempts;


    public QuizAttempt(
            String id,
            String quizId,
            QuizAttemptStatus status,
            List<QuestionAttempt> questionAttempts
    ) {

        this.id = id;
        this.quizId = quizId;
        this.status = status;
        this.questionAttempts = questionAttempts;
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

    public List<QuestionAttempt> getQuestionAttempts() {
        return questionAttempts;
    }
}
