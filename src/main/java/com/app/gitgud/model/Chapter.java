package com.app.gitgud.model;

public class Chapter {
    private String id;
    private String title;
    private int order;

    public Chapter() {}

    public Chapter(String id, String title, int order) {
        this.id = id;
        this.title = title;
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
