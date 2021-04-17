package com.backend.fakedb.entities;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
class UserPreferencesPK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private ProviderEntity providerEntity;


}

@Entity
@Table
public class UserPreferencesEntity {

    @EmbeddedId
    private UserPreferencesPK id;

    // Is the user subscribed to this particular provider?
    boolean subscribed;

    public UserPreferencesEntity() {
    }

    public UserPreferencesEntity(UserPreferencesPK id, boolean subscribed) {
        this.id = id;
        this.subscribed = subscribed;
    }

    public UserPreferencesPK getId() {
        return id;
    }

    public void setId(UserPreferencesPK id) {
        this.id = id;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }
}
