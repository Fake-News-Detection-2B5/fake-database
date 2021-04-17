package com.backend.fakedb.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table
public class PostEntity {

    // The associated ID
    @Id
    @GeneratedValue
    Integer id;

    // A URL to a thumbnail picture
    String thumbnail;

    // A title for the post
    String title;

    // A short description associated with the current post
    String description;

    // The date when the post was published
    Date postDate;

    // The associated score (ranges in values from 0 to 100, 0 meaning UNDOUBTEDLY FALSE, and 100 meaning UNDOUBTEDLY TRUE.
    double score;

    // The source URL
    String sourceUrl;

    public PostEntity() {
    }

    public PostEntity(Integer id, String thumbnail, String title, String description, Date postDate, double score, String sourceUrl) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.title = title;
        this.description = description;
        this.postDate = postDate;
        this.score = score;
        this.sourceUrl = sourceUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
