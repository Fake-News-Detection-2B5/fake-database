package com.backend.fakedb.entities;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public
class UserPreferencesPK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private ProviderEntity providerEntity;

    public UserPreferencesPK() {
    }

    public UserPreferencesPK(UserEntity userEntity, ProviderEntity providerEntity) {
        this.userEntity = userEntity;
        this.providerEntity = providerEntity;
    }

    public int getUserID() {
        return userEntity.getId();
    }

    public int getProviderID() {
        return providerEntity.getId();
    }
}
