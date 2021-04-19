package com.backend.fakedb.repositories;

import com.backend.fakedb.entities.UserPreferencesEntity;
import com.backend.fakedb.entities.UserPreferencesPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPreferencesRepository extends JpaRepository<UserPreferencesEntity, UserPreferencesPK> {
}
