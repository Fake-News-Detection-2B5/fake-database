package com.backend.fakedb.entities;

import javax.persistence.*;

@Entity
@Table
public class ProviderEntity {

    // The associated id
    @Id
    @GeneratedValue
    @Column(name = "provider_id")
    Integer id;

    // The provider's name (for example BBC)
    String name;

    // The average credibility based on all the posts gathered from this source
    double credibility;

    // A URL to the avatar picture of this provider
    String avatar;


    public ProviderEntity() {

    }

    public ProviderEntity(Integer id, String name, double credibility, String avatar) {
        this.id = id;
        this.name = name;
        this.credibility = credibility;
        this.avatar = avatar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCredibility() {
        return credibility;
    }

    public void setCredibility(double credibility) {
        this.credibility = credibility;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
