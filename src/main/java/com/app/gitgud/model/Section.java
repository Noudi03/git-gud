package com.app.gitgud.model;

public class Section {

    private String id;
    private String title;
    private String content;
    private int order;
    private String advancedContent;

    public Section() {
    }

    public Section(
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

    public void setId(
            String id
    ) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(
            String title
    ) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(
            String content
    ) {
        this.content = content;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(
            int order
    ) {
        this.order = order;
    }

    public String getAdvancedContent() {
        return advancedContent;
    }

    public void setAdvancedContent(
            String advancedContent
    ) {
        this.advancedContent = advancedContent;
    }
}
