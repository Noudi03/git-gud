package com.app.gitgud.model.dto;

import com.app.gitgud.model.TrialDifficulty;
import com.app.gitgud.model.TrialType;

import java.util.List;
import java.util.Map;

public class MultipleChoiceTrialDto
        implements TrialDto {

    private final String id;
    private final String text;
    private final Map<String, String> options;
    private final String correctAnswer;
    private final TrialDifficulty difficulty;
    private final List<String> hints;
    private final int order;

    public MultipleChoiceTrialDto(
            String id,
            String text,
            Map<String, String> options,
            String correctAnswer,
            TrialDifficulty difficulty,
            List<String> hints,
            int order
    ) {
        this.id = id;
        this.text = text;
        this.options = options;
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

    public Map<String, String> getOptions() {
        return options;
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
        return TrialType.MULTIPLE_CHOICE;
    }
}
