package com.app.gitgud.model;

public class Section {
    private String id;
    private String chapterId;
    private String title;
    private String content;
    private int order;

    public Section() {}

    public Section(String id, String chapterId, String title, String content, int order) {
        this.id = id;
        this.chapterId = chapterId;
        this.title = title;
        this.content = content;
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
