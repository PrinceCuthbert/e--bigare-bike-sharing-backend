package com.cuthbert.e__bigare_bike_sharing.service;

import com.cuthbert.e__bigare_bike_sharing.model.Ride;
import com.cuthbert.e__bigare_bike_sharing.model.RideCharge;
import com.cuthbert.e__bigare_bike_sharing.model.RideStatus;
import com.cuthbert.e__bigare_bike_sharing.repository.RideChargeRepository;
import com.cuthbert.e__bigare_bike_sharing.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RideChargeService {

    @Autowired
    private RideChargeRepository rideChargeRepository;

    @Autowired
    private RideRepository rideRepository;

    //E1: Write a ride charge
    public String saveRideCharge(UUID rideId, RideCharge charge){
        Optional<Ride> thisRide=rideRepository.findById(rideId);
        if( thisRide.isEmpty()){
            return "The ride with that id does not exist";
        }
        Ride ride =thisRide.get();
        if(ride.getStatus() != RideStatus.RETURNED){
            return "Ride charges are only for RETURNED rides";
        }
        charge.setRide(ride);
        charge.setChargedDate(LocalDate.now());
        rideChargeRepository.save(charge);

        return "Ride charge saved successfully";
    }

    // E2: History of rider charges
    public List<RideCharge> getChargesByRiderCode(String riderCode){
        return rideChargeRepository.findByRiderCodeNewestFirst(riderCode);
    }

    // E3: Delete a ride charge
    public String deleteRideCharge(UUID id){
        Optional<RideCharge> charge=rideChargeRepository.findById(id);
        if(charge.isEmpty()){
            return "The ride charge with that id does not exist";
        }
        rideChargeRepository.delete(charge.get());
        return "Ride charge deleted successfully";
    }
}
