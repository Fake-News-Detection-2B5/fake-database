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

    // TO BE IMPLEMENTED!

    //@Query(value = "select * from ProviderEntity p limit :countParam offset :skipParam", nativeQuery = true)
    // @Query("select p from ProviderEntity p order by p.name")
    // @Query(value="SELECT p FROM ProviderEntity p ORDER BY p.provider_id offset ?1 limit ?2", nativeQuery = true)
    // List<ProviderEntity> getWithCount(int skip, int count);

    @Modifying
    @Query("UPDATE ProviderEntity p SET p.name = ?2, p.credibility = ?3, p.avatar = ?4 WHERE p.id = ?1")
    void update(Integer id, String name, double credibility, String avatar);
}
