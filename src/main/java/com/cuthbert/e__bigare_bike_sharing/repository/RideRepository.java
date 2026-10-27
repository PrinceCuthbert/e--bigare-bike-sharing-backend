package com.cuthbert.e__bigare_bike_sharing.repository;

import com.cuthbert.e__bigare_bike_sharing.model.Ride;
import com.cuthbert.e__bigare_bike_sharing.model.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RideRepository extends JpaRepository<Ride, UUID> {

    // B2: Rides by status, earliest rideDate first
    List<Ride>findByStatusOrderByRideDateAsc(RideStatus status);

    // B3: Rides in a date range (inclusive), ordered by rideDate Ascending
    List<Ride> findByRideDateBetweenOrderByRideDateAsc(LocalDate start,LocalDate end);

}
