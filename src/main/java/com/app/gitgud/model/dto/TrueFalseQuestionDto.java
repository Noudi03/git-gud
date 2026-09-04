package com.app.gitgud.model.dto;

import com.app.gitgud.model.QuestionDifficulty;
import com.app.gitgud.model.QuestionType;

import java.util.List;

public class TrueFalseQuestionDto
        implements QuestionDto {

    private final String id;
    private final String text;
    private final boolean correctAnswer;
    private final QuestionDifficulty difficulty;
    private final List<String> hints;

    public TrueFalseQuestionDto(
            String id,
            String text,
            boolean correctAnswer,
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

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public QuestionType getType() {
        return QuestionType.TRUE_FALSE;
    }

    @Override
    public QuestionDifficulty getDifficulty() {
        return difficulty;
    }

    public List<String> getHints() {
        return hints;
    }
}
