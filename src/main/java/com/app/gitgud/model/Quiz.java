package com.app.gitgud.model;

public class Quiz {

    private String id;
    private String title;
    private QuizType type;
    private int order;
    private QuizContext context;

    public Quiz() {
    }

    public Quiz(
            String id,
            String title,
            QuizType type,
            int order,
            QuizContext context
    ) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.order = order;
        this.context = context;
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

    public QuizContext getContext() {
        return context;
    }

    public void setContext(QuizContext context) {
        this.context = context;
    }
}
