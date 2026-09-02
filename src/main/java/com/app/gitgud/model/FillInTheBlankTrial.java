package com.app.gitgud.model;

import java.util.List;

public class FillInTheBlankTrial
        implements Trial {

    private String id;
    private String text;
    private String correctAnswer;
    private TrialDifficulty difficulty;
    private List<String> hints;
    private int order;

    public FillInTheBlankTrial() {
    }

    public FillInTheBlankTrial(
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
        return TrialType.FILL_IN_THE_BLANK;
    }
}
