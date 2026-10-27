package com.cuthbert.e__bigare_bike_sharing.service;

import com.cuthbert.e__bigare_bike_sharing.model.Ride;
import com.cuthbert.e__bigare_bike_sharing.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    public String saveRide(Ride ride){
        rideRepository.save(ride);
        return "Ride saved successfully";
    }

    public List<Ride> getAllRides(){
        return rideRepository.findAll();
    }

    public Optional<Ride> getRideById(UUID id){
        return rideRepository.findById(id);
    }

    public String updateRide(UUID id,Ride updatedRide){
        Optional<Ride> ride=rideRepository.findById(id);

        if(ride.isEmpty()){
            return "The ride with that id does not exist";
        }

        Ride existingRide=ride.get();

        existingRide.setRideDate(updatedRide.getRideDate());
        existingRide.setStatus(updatedRide.getStatus());
        existingRide.setDestination(updatedRide.getDestination());
        existingRide.setDurationMinutes(updatedRide.getDurationMinutes());
        existingRide.setRider(updatedRide.getRider());
        existingRide.setSteward(updatedRide.getSteward());

        rideRepository.save(existingRide);

        return "Ride updated successfully";
    }

    public String deleteRide(UUID id){
        Optional<Ride> ride=rideRepository.findById(id);

        if(ride.isEmpty()){
            return "The ride with that id does not exist";
        }

        rideRepository.delete(ride.get());
        return "Ride deleted successfully";
    }


}
