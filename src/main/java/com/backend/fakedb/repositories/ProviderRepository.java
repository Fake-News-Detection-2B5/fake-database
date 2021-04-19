package com.backend.fakedb.repositories;

import com.backend.fakedb.entities.ProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderRepository extends JpaRepository<ProviderEntity, Integer> {

    @Modifying
    @Query("UPDATE ProviderEntity p SET p.name = ?2, p.credibility = ?3, p.avatar = ?4 WHERE p.id = ?1")
    void update(Integer id, String name, double credibility, String avatar);
}
