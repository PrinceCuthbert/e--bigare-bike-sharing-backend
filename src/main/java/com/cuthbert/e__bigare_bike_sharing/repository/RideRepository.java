package com.cuthbert.e__bigare_bike_sharing.repository;

import com.cuthbert.e__bigare_bike_sharing.model.Ride;
import com.cuthbert.e__bigare_bike_sharing.model.RideStatus;
import com.cuthbert.e__bigare_bike_sharing.model.Steward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RideRepository extends JpaRepository<Ride, UUID> {

    // B2: Rides by status, earliest rideDate first
    List<Ride>findByStatusOrderByRideDateAsc(RideStatus status);

    // B3: Rides in a date range (inclusive), ordered by rideDate Ascending, so hano the key take away is that this JPQL is equivalent to
    // select * from ride where ride_date between 'start' AND 'end' ORDER BY ride_date ASC; eg: select * from ride where ride_date between '2026-10-01' AND '2026-10-31' ORDER BY ride_date ASC;
    List<Ride> findByRideDateBetweenOrderByRideDateAsc(LocalDate start,LocalDate end);

    // D1: Check if steward already has a ride on that date whose status is NOT ABORTED
    boolean existsByStewardAndRideDateAndStatusNot(Steward steward,LocalDate rideDate,RideStatus status);

}
