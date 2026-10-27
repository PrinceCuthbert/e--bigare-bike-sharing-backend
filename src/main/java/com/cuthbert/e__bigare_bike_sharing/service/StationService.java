package com.cuthbert.e__bigare_bike_sharing.service;

import com.cuthbert.e__bigare_bike_sharing.model.Station;
import com.cuthbert.e__bigare_bike_sharing.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    public String saveStation(Station station){
        if (stationRepository.existsByStationNumber(station.getStationNumber())) {
            return "Station number already exists";
        }
        stationRepository.save(station);
        return "Station saved successfully";
    }

    public List<Station> getAllStations(){
        return stationRepository.findAll();
    }

    public Optional<Station>getStationById(UUID id){
        return stationRepository.findById(id);
    }


    public String updateStation(UUID id,Station updatedStation){
        Optional<Station> station=stationRepository.findById(id);
        if(station.isEmpty()){
            return "The station with that id does not exist";
        }

        Station exists=station.get();
        if (exists.getStationNumber() != updatedStation.getStationNumber() &&
                stationRepository.existsByStationNumber(updatedStation.getStationNumber())) {
            return "Station number already exists";
        }
        exists.setStationNumber(updatedStation.getStationNumber());
        exists.setName(updatedStation.getName());
        stationRepository.save(exists);

        return "Station updated successfully";

    }

    public String deleteStation(UUID id){
        Optional<Station> stationToDelete=stationRepository.findById(id);
        if(stationToDelete.isEmpty()){
            return "The station with that id does not exist";
        }
        stationRepository.delete(stationToDelete.get());
        return "Station deleted successfully";
    }
}
