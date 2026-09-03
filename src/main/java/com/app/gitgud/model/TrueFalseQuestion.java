package com.app.gitgud.model;

import java.util.List;

public class TrueFalseQuestion
        implements Question {

    private String id;
    private String text;
    private boolean correctAnswer;
    private QuestionDifficulty difficulty;
    private List<String> hints;
    private int order;

    public TrueFalseQuestion() {
    }

    public TrueFalseQuestion(
            String id,
            String text,
            boolean correctAnswer,
            QuestionDifficulty difficulty,
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

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(
            boolean correctAnswer
    ) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public QuestionType getType() {
        return QuestionType.TRUE_FALSE;
    }

    @Override
    public QuestionDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(
            QuestionDifficulty difficulty
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
}
