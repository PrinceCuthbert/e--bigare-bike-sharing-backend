package com.cuthbert.e__bigare_bike_sharing.repository;

import com.cuthbert.e__bigare_bike_sharing.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RiderRepository extends JpaRepository<Rider, UUID> {
    boolean existsByRiderCode(String riderCode);

    boolean existsByPhone(String phone);

    // B1: Find riders by lastName, case-insensitive, ordered by firstName Ascending
    List<Rider> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);


    // C4: Distinct riders who have had a ride with this steward
    @Query("SELECT DISTINCT r.rider FROM Ride r WHERE r.steward.id = :stewardId")
    List<Rider> findRidersByStewardId(@Param("stewardId") UUID stewardId);
}
