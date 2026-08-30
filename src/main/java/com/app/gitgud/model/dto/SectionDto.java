package com.app.gitgud.model.dto;

public class SectionDto {

    private final String id;
    private final String chapterId;
    private final String title;
    private final String content;
    private final int order;
    private final String prerequisiteId;
    private final boolean locked;
    private final String advancedContent;

    public SectionDto(
            String id,
            String chapterId,
            String title,
            String content,
            int order,
            String prerequisiteId,
            boolean locked,
            String advancedContent
    ) {
        this.id = id;
        this.chapterId = chapterId;
        this.title = title;
        this.content = content;
        this.order = order;
        this.prerequisiteId = prerequisiteId;
        this.locked = locked;
        this.advancedContent = advancedContent;
    }

    public String getId() {
        return id;
    }

    public String getChapterId() {
        return chapterId;
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

    public String getPrerequisiteId() {
        return prerequisiteId;
    }

    public boolean isLocked() {
        return locked;
    }

    public String getAdvancedContent() {
        return advancedContent;
    }
}
