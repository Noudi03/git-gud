package com.app.gitgud.model.dto;

public class ChapterDto {

    private final String id;
    private final String title;
    private final int order;

    public ChapterDto(
            String id,
            String title,
            int order
    ) {
        this.id = id;
        this.title = title;
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getOrder() {
        return order;
    }
}
