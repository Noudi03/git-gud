package com.app.gitgud.model;

import java.util.List;

public class FillInTheBlankQuestion
        implements Question {

    private String id;
    private String text;
    private String correctAnswer;
    private QuestionDifficulty difficulty;
    private List<String> hints;

    public FillInTheBlankQuestion() {
    }

    public FillInTheBlankQuestion(
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
    public QuestionType getType() {
        return QuestionType.FILL_IN_THE_BLANK;
    }
}
