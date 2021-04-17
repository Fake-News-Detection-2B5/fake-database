package com.backend.fakedb.entities;

import javax.persistence.*;

@Entity
@Table
public class UserEntity {

    // The associated ID
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    Integer id;

    // The username of this user
    String username;

    /* A hash for the associated password
    * String passwordHash;
    * */

    // A URL to the user's avatar picture.
    String avatarUrl;

    // A short bio describing the user
    String bio;

    // The associated email
    String email;

    public UserEntity() {
    }

    public UserEntity(Integer id, String username, String avatarUrl, String bio, String email) {
        this.id = id;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.bio = bio;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
