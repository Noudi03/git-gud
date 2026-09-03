package com.app.gitgud.model.dto;

import com.app.gitgud.model.QuestionDifficulty;
import com.app.gitgud.model.QuestionType;

import java.util.List;
import java.util.Map;

public class MultipleChoiceQuestionDto
        implements QuestionDto {

    private final String id;
    private final String text;
    private final Map<String, String> options;
    private final String correctAnswer;
    private final QuestionDifficulty difficulty;
    private final List<String> hints;
    private final int order;

    public MultipleChoiceQuestionDto(
            String id,
            String text,
            Map<String, String> options,
            String correctAnswer,
            QuestionDifficulty difficulty,
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
    public QuestionDifficulty getDifficulty() {
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
    public QuestionType getType() {
        return QuestionType.MULTIPLE_CHOICE;
    }
}
