package com.app.gitgud.model.dto;

public class SectionDto {

    private final String id;
    private final String title;
    private final String content;
    private final int order;
    private final String advancedContent;

    public SectionDto(
            String id,
            String title,
            String content,
            int order,
            String advancedContent
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.order = order;
        this.advancedContent = advancedContent;
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

    public String getAdvancedContent() {
        return advancedContent;
    }
}
