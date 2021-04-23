package com.backend.fakedb.entities;

import java.sql.Date;

/**
 * Entity representing the information coming from the Ingestion System
 */
public class IngestionEntity {
    Integer id;

    String title;

    String thumbnail;

    String content;

    String description;

    Date postDate;

    String sourceUrl;

    public IngestionEntity() {
    }

    public IngestionEntity(Integer id, String title, String thumbnail, String content, String description, Date postDate, String sourceUrl) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.content = content;
        this.description = description;
        this.postDate = postDate;
        this.sourceUrl = sourceUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
