package com.app.gitgud.model.dto;

import com.app.gitgud.model.QuestionDifficulty;
import com.app.gitgud.model.QuestionType;

import java.util.List;

public class FillInTheBlankQuestionDto
        implements QuestionDto {

    private final String id;
    private final String text;
    private final String correctAnswer;
    private final QuestionDifficulty difficulty;
    private final List<String> hints;

    public FillInTheBlankQuestionDto(
            String id,
            String text,
            String correctAnswer,
            QuestionDifficulty difficulty,
            List<String> hints
    ) {

        this.id = id;
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
        this.hints = hints;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public QuestionDifficulty getDifficulty() {
        return difficulty;
    }

    public List<String> getHints() {
        return hints;
    }

    @Override
    public QuestionType getType() {
        return QuestionType.FILL_IN_THE_BLANK;
    }
}
