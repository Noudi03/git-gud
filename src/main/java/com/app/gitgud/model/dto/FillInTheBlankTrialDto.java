package com.app.gitgud.model.dto;

import com.app.gitgud.model.TrialDifficulty;
import com.app.gitgud.model.TrialType;

import java.util.List;

public class FillInTheBlankTrialDto
        implements TrialDto {

    private final String id;
    private final String text;
    private final String correctAnswer;
    private final TrialDifficulty difficulty;
    private final List<String> hints;
    private final int order;

    public FillInTheBlankTrialDto(
            String id,
            String text,
            String correctAnswer,
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

    public String getCorrectAnswer() {
        return correctAnswer;
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

    @Override
    public TrialType getType() {
        return TrialType.FILL_IN_THE_BLANK;
    }
}
