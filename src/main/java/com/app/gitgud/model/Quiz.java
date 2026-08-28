package com.app.gitgud.model;

public class Quiz {

    private String id;
    private String title;
    private QuizType type;
    private int order;

    public Quiz() {
    }

    public Quiz(
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

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public QuizType getType() {
        return type;
    }

    public void setType(QuizType type) {
        this.type = type;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
