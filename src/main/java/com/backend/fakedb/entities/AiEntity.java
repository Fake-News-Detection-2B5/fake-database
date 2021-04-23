package com.backend.fakedb.entities;

/**
 * Class that stores information which will be sent to the AI module for processing.
 */
public class AiEntity {
    String title;
    String content;

    public AiEntity() {
    }

    public AiEntity(String title, String content) {
        this.title = title;
        this.content = content;
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
}
