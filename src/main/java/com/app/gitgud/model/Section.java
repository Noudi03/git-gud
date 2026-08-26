package com.app.gitgud.model;

public class Section {
    private String id;
    private String chapterId;
    private String title;
    private String content;
    private int order;
    private String prerequisiteId;
    private boolean locked;
    private String advancedContent;

    public Section() {}

    public Section(String id, String chapterId, String title, String content, int order, String prerequisiteId, boolean locked, String advancedContent) {
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

    public String getPrerequisiteId() {
        return prerequisiteId;
    }

    public void setPrerequisiteId(String prerequisiteId) {
        this.prerequisiteId = prerequisiteId;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getAdvancedContent() {
        return advancedContent;
    }

    public void setAdvancedContent(String advancedContent) {
        this.advancedContent = advancedContent;
    }
}
