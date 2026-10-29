package com.cuthbert.e__bigare_bike_sharing.service;

import com.cuthbert.e__bigare_bike_sharing.model.BikeType;
import com.cuthbert.e__bigare_bike_sharing.model.Station;
import com.cuthbert.e__bigare_bike_sharing.model.Steward;
import com.cuthbert.e__bigare_bike_sharing.repository.BikeTypeRepository;
import com.cuthbert.e__bigare_bike_sharing.repository.StationRepository;
import com.cuthbert.e__bigare_bike_sharing.repository.StewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StewardService {

    @Autowired
    private StewardRepository stewardRepository;

    @Autowired
    private StationRepository stationRepository;


    @Autowired
    private BikeTypeRepository bikeTypeRepository;

    public String saveSteward(Steward steward) {
        if (steward.getPhone() != null && stewardRepository.existsByPhone(steward.getPhone())) {
            return "Steward phone already exists";
        }

        // Attach existing managed Station
        if (steward.getStation() != null && steward.getStation().getId() != null) {
            stationRepository.findById(steward.getStation().getId()).ifPresent(steward::setStation);
        } else {
            steward.setStation(null);
        }

        // Attach existing managed BikeTypes
        if (steward.getBikeTypes() != null && !steward.getBikeTypes().isEmpty()) {
            List<BikeType> managedList = new ArrayList<>();
            for (BikeType bt : steward.getBikeTypes()) {
                if (bt.getId() != null) {
                    bikeTypeRepository.findById(bt.getId()).ifPresent(managedList::add);
                }
            }
            steward.setBikeTypes(managedList);
        }

        stewardRepository.save(steward);
        return "Steward saved successfully";
    }

    public List<Steward> getAllStewards(){
        return stewardRepository.findAll();
    }

    public Optional<Steward> getStewardById(UUID id){
        return stewardRepository.findById(id);
    }

    public String updatedSteward(UUID id,Steward updatedSteward){
        Optional<Steward> steward=stewardRepository.findById(id);

        if(steward.isEmpty()){
            return "The steward with that id does not exist";
        }

        Steward exists=steward.get();

        if (updatedSteward.getPhone() != null &&
                !updatedSteward.getPhone().equals(exists.getPhone()) &&
                stewardRepository.existsByPhone(updatedSteward.getPhone())) {
            return "Steward phone already exists";
        }

        exists.setFirstName(updatedSteward.getFirstName());
        exists.setLastName(updatedSteward.getLastName());
        exists.setPhone(updatedSteward.getPhone());

        if (updatedSteward.getStation() != null && updatedSteward.getStation().getId() != null) {
            stationRepository.findById(updatedSteward.getStation().getId()).ifPresent(exists::setStation);
        } else {
            exists.setStation(null);
        }

        if (updatedSteward.getBikeTypes() != null) {
            List<BikeType> managedList = new ArrayList<>();
            for (BikeType bt : updatedSteward.getBikeTypes()) {
                if (bt.getId() != null) {
                    bikeTypeRepository.findById(bt.getId()).ifPresent(managedList::add);
                }
            }
            exists.setBikeTypes(managedList);
        }

        stewardRepository.save(exists);

        return "Steward updated successfully";
    }

    public String deleteSteward(UUID id){
        Optional<Steward> steward=stewardRepository.findById(id);

        if(steward.isEmpty()){
            return "The steward with that id does not exist";
        }
        stewardRepository.delete(steward.get());
        return "Steward deleted successfully";
    }


    // Here is a C1: service for stewards by bike type
    public List<Steward> getStewardsByBikeType(String name){
        return stewardRepository.findStewardByBikeTypeName(name);
    }

    // D3: Add one bike type to a steward
    public String addBikeTypeToSteward(UUID stewardId,UUID bikeTypeId){
        Optional<Steward> steward=stewardRepository.findById(stewardId);

        if(steward.isEmpty()){
            return "The steward with that id does not exist";
        }

        Optional<BikeType> bikeType=bikeTypeRepository.findById(bikeTypeId);
        if(bikeType.isEmpty()){
            return "That bike type does not exist";
        }

        Steward thisSteward=steward.get();
        BikeType thisBikeType=bikeType.get();

        // I will check if the steward(thisSteward) already holds this bike type

        for(BikeType bt: thisSteward.getBikeTypes()){
            if(bt.getId().equals(bikeTypeId)){
                return "The steward already has that bike type";
            }

        }

        thisSteward.getBikeTypes().add(thisBikeType);
        stewardRepository.save(thisSteward);

        return "Bike type added to the steward";
    }

    // D4: Move a steward to another station

    public String moveStewardToStation(UUID stewardId,int stationNumber){
        Optional<Steward> steward=stewardRepository.findById(stewardId);

        if(steward.isEmpty()){
            return "The steward with that id does not exist";
        }

        Optional<Station> station=stationRepository.findByStationNumber(stationNumber);

        if(station.isEmpty()){
            return "The station with that stationNumber does not exist";
        }

        Station targetStation=station.get();
        Steward targetSteward=steward.get();

        // Check if the station is already occupied by another steward

        if(stewardRepository.existsByStation(targetStation)){
            if(targetSteward.getStation() ==  null || !targetSteward.getStation().getId().equals(targetStation.getId())){
                return "That station is taken by another steward";
            }
        }

        targetSteward.setStation(targetStation);
        stewardRepository.save(targetSteward);

        return "Station changed successfully";
    }

}
