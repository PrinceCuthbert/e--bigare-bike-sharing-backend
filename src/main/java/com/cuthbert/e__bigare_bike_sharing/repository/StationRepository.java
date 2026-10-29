package com.cuthbert.e__bigare_bike_sharing.repository;

import com.cuthbert.e__bigare_bike_sharing.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StationRepository extends JpaRepository<Station, UUID> {
    boolean existsByStationNumber(int stationNumber);
    Optional<Station> findByStationNumber(int stationNumber);

    // C2: Free Stations(typically hano we wat to find Stations with no steward, sorted by stationNumber)
    @Query("SELECT s FROM Station s WHERE NOT EXISTS (SELECT st FROM Steward  st WHERE st.station=s) ORDER BY s.stationNumber ASC")
    List<Station> findFreeStations();


}


