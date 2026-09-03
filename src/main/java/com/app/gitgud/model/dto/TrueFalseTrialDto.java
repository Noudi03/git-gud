package com.app.gitgud.model.dto;

import com.app.gitgud.model.TrialDifficulty;
import com.app.gitgud.model.TrialType;

import java.util.List;

public class TrueFalseTrialDto
        implements TrialDto {

    private final String id;
    private final String text;
    private final boolean correctAnswer;
    private final TrialDifficulty difficulty;
    private final List<String> hints;
    private final int order;

    public TrueFalseTrialDto(
            String id,
            String text,
            boolean correctAnswer,
            TrialDifficulty difficulty,
            List<String> hints,
            int order
    ) {
        this.id = id;
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
        this.hints = hints;
        this.order = order;
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
    public TrialType getType() {
        return TrialType.TRUE_FALSE;
    }

    @Override
    public TrialDifficulty getDifficulty() {
        return difficulty;
    }

    public List<String> getHints() {
        return hints;
    }

    @Override
    public int getOrder() {
        return order;
    }
}
