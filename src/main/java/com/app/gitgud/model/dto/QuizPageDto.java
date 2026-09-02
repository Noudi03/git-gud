package com.app.gitgud.model.dto;

import java.util.List;

public class QuizPageDto {

    private final QuizDto quiz;
    private final List<TrialDto> trials;

    public QuizPageDto(
            QuizDto quiz,
            List<TrialDto> trials
    ) {

        this.quiz = quiz;
        this.trials = trials;
    }

    public QuizDto getQuiz() {
        return quiz;
    }

    public List<TrialDto> getTrials() {
        return trials;
    }
}
