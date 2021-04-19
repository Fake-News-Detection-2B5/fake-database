package com.backend.fakedb.repositories;

import com.backend.fakedb.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Modifying
    @Query("UPDATE UserEntity u SET u.bio = ?1 WHERE u.id = ?2")
    void update(String bio, Integer id);
}
