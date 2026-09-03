package com.app.gitgud.model;

import java.util.List;
import java.util.Map;

public class MultipleChoiceTrial implements Trial {

    private String id;
    private String text;
    private Map<String, String> options;
    private String correctAnswer;
    private TrialDifficulty difficulty;
    private List<String> hints;
    private int order;

    public MultipleChoiceTrial() {
    }

    public MultipleChoiceTrial(
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

    public void setId(
            String id
    ) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(
            String text
    ) {
        this.text = text;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(
            Map<String, String> options
    ) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(
            String correctAnswer
    ) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public TrialDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(
            TrialDifficulty difficulty
    ) {
        this.difficulty = difficulty;
    }

    public List<String> getHints() {
        return hints;
    }

    public void setHints(
            List<String> hints
    ) {
        this.hints = hints;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(
            int order
    ) {
        this.order = order;
    }

    @Override
    public TrialType getType() {
        return TrialType.MULTIPLE_CHOICE;
    }
}
