package com.cuthbert.e__bigare_bike_sharing.repository;

import com.cuthbert.e__bigare_bike_sharing.model.BikeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BikeTypeRepository extends JpaRepository<BikeType, UUID> {
        boolean existsByNameIgnoreCase(String name);

        // C3: Bike types nobody holds so the equivalent of the postgres query is this: SELECT b.*
        //FROM bike_type b
        //WHERE NOT EXISTS (
        //    SELECT 1
        //    FROM steward_bike_type s
        //    WHERE s.bike_type_id = b.id
        //);
        @Query("SELECT b FROM BikeType b WHERE b.stewards IS EMPTY")
        List<BikeType> findUnusedBikeTypes();
}
