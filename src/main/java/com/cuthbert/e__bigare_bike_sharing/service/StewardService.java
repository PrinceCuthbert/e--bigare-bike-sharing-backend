package com.cuthbert.e__bigare_bike_sharing.service;

import com.cuthbert.e__bigare_bike_sharing.model.Steward;
import com.cuthbert.e__bigare_bike_sharing.repository.StewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StewardService {

    @Autowired
    private StewardRepository stewardRepository;

    public String saveSteward(Steward steward){
        if (steward.getPhone() != null && stewardRepository.existsByPhone(steward.getPhone())) {
            return "Steward phone already exists";
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
        exists.setStation(updatedSteward.getStation());
        exists.setBikeTypes(updatedSteward.getBikeTypes());

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

}
