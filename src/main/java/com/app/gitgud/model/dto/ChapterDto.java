package com.app.gitgud.model.dto;

public class ChapterDto {

    private final String id;
    private final String title;
    private final String content;
    private final int order;

    public ChapterDto(
            String id,
            String title,
            String content,
            int order
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getOrder() {
        return order;
    }
}
