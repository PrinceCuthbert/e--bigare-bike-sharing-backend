package com.cuthbert.e__bigare_bike_sharing.repository;

import com.cuthbert.e__bigare_bike_sharing.model.BikeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BikeTypeRepository extends JpaRepository<BikeType, UUID> {
        boolean existsByNameIgnoreCase(String name);
}
