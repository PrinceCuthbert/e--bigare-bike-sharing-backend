package com.cuthbert.e__bigare_bike_sharing.repository;

import com.cuthbert.e__bigare_bike_sharing.model.Steward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface StewardRepository extends JpaRepository<Steward, UUID> {
    boolean existsByPhone(String phone);

    // C1: Stewards by bike type case-insensitive
    @Query("SELECT DISTINCT s FROM Steward s JOIN s.bikeTypes b WHERE LOWER(b.name)=LOWER(:name) ")
    List<Steward> findStewardByBikeTypeName(@Param("name") String name);
}
