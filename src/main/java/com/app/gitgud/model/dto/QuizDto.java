package com.app.gitgud.model.dto;

import com.app.gitgud.model.QuizType;

public class QuizDto {

    private final String id;
    private final String title;
    private final QuizType type;
    private final int order;

    public QuizDto(
            String id,
            String title,
            QuizType type,
            int order
    ) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public QuizType getType() {
        return type;
    }

    public int getOrder() {
        return order;
    }
}
