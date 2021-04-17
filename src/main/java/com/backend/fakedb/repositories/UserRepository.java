package com.backend.fakedb.repositories;

import com.backend.fakedb.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Modifying
    @Query("UPDATE UserEntity u SET u.username = ?1, u.avatarUrl = ?2, u.bio = ?3, u.email = ?4 WHERE u.id = ?5")
    void update(String username, String avatarUrl, String bio, String email, Integer id);
}
