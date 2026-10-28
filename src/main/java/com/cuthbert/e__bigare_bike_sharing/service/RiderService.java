package com.cuthbert.e__bigare_bike_sharing.service;

import com.cuthbert.e__bigare_bike_sharing.model.Rider;
import com.cuthbert.e__bigare_bike_sharing.model.Steward;
import com.cuthbert.e__bigare_bike_sharing.repository.RiderRepository;
import com.cuthbert.e__bigare_bike_sharing.repository.StewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RiderService {

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private StewardRepository stewardRepository;

    public String saveRider(Rider rider){
        if(riderRepository.existsByRiderCode(rider.getRiderCode())){
            return "Rider code already exists";
        }
        if(rider.getPhone() != null && riderRepository.existsByPhone(rider.getPhone())){
            return "Rider phone already exists";
        }
        riderRepository.save(rider);

        return "Rider saved successfully";
    }

    public List<Rider> getAllRiders(){
        return riderRepository.findAll();
    }

    public Optional<Rider> getRiderById(UUID id){
        return riderRepository.findById(id);
    }

    public String updatedRider(UUID id,Rider updatedRider){
        Optional<Rider> riderExists=riderRepository.findById(id);

        if(riderExists.isEmpty()){
            return "The rider with that id does not exist";
        }
        Rider existingRider=riderExists.get();

        if (!existingRider.getRiderCode().equals(updatedRider.getRiderCode()) &&
                riderRepository.existsByRiderCode(updatedRider.getRiderCode())) {
            return "Rider code already exists";
        }
        if (updatedRider.getPhone() != null &&
                !updatedRider.getPhone().equals(existingRider.getPhone()) &&
                riderRepository.existsByPhone(updatedRider.getPhone())) {
            return "Rider phone already exists";
        }

        existingRider.setFirstName(updatedRider.getFirstName());
        existingRider.setLastName(updatedRider.getLastName());
        existingRider.setRiderCode(updatedRider.getRiderCode());
        existingRider.setPhone(updatedRider.getPhone());

        riderRepository.save(existingRider);

        return "Rider updated successfully";
    }

    public String deleteRider(UUID id){
        Optional<Rider> riderExists=riderRepository.findById(id);

        if(riderExists.isEmpty()){
            return "The rider with that id does not exist";
        }
        riderRepository.delete(riderExists.get());

        return "Rider deleted successfully";
    }

    // B1.Find riders by lastName
    public List<Rider>getRidersByLastName(String lastName){
        return riderRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    // C4. Get Riders associated with a certain steward
    public Optional<List<Rider>> getRidersOfSteward(UUID stewardId){

        // Hano we used Optiona,empty instead of null cz we can have to return 2 types of distinguished response
        // either Not Found(steward id is not found) or Found but Empty(to mean there is no riders associated to that steward, niyo mpamvu, here I used Optional.empty();
        // So If your service simply returned List<Rider>, the controller would receive an empty list [] in both cases and could not tell whether to return 404 or 200.
        if(!stewardRepository.existsById(stewardId)){
            return Optional.empty();
        }
        return  Optional.of(riderRepository.findRidersByStewardId(stewardId));
    }
}
