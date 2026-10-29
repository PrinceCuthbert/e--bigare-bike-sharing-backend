package com.cuthbert.e__bigare_bike_sharing.repository;

import com.cuthbert.e__bigare_bike_sharing.model.Ride;
import com.cuthbert.e__bigare_bike_sharing.model.RideCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RideChargeRepository extends JpaRepository<RideCharge, UUID> {

    // E2: Ride charge history of a rider(JPQL path navigation through ride -> rider -> riderCode, newest chargedDate first)
    @Query("SELECT rc FROM RideCharge rc WHERE rc.ride.rider.riderCode = :riderCode ORDER BY rc.chargedDate DESC")
    List<RideCharge> findByRiderCodeNewestFirst(@Param("riderCode") String riderCode);

    // E3: Derived query to check if a ride has associated charges before deleting
    boolean existsByRide(Ride ride);
}
