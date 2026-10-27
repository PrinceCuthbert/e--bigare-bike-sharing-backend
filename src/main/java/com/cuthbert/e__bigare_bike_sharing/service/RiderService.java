package com.cuthbert.e__bigare_bike_sharing.service;

import com.cuthbert.e__bigare_bike_sharing.model.Rider;
import com.cuthbert.e__bigare_bike_sharing.repository.RiderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RiderService {

    @Autowired
    private RiderRepository riderRepository;

    public String saveRider(Rider rider){
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
}
