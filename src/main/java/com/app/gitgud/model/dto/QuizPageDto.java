package com.app.gitgud.model.dto;

import java.util.List;

public class QuizPageDto {

    private final QuizDto quiz;
    private final List<QuestionDto> questions;


    public QuizPageDto(
            QuizDto quiz,
            List<QuestionDto> questions
    ) {

        this.quiz = quiz;
        this.questions = questions;
    }


    public QuizDto getQuiz() {
        return quiz;
    }


    public List<QuestionDto> getQuestions() {
        return questions;
    }
}
