package com.cuthbert.e__bigare_bike_sharing.service;

import com.cuthbert.e__bigare_bike_sharing.model.Ride;
import com.cuthbert.e__bigare_bike_sharing.model.RideStatus;
import com.cuthbert.e__bigare_bike_sharing.model.Rider;
import com.cuthbert.e__bigare_bike_sharing.model.Steward;
import com.cuthbert.e__bigare_bike_sharing.repository.RideChargeRepository;
import com.cuthbert.e__bigare_bike_sharing.repository.RideRepository;
import com.cuthbert.e__bigare_bike_sharing.repository.RiderRepository;
import com.cuthbert.e__bigare_bike_sharing.repository.StewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;


    @Autowired
    private StewardRepository stewardRepository;

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private RideChargeRepository rideChargeRepository;

    // Now I will improve it to save saving ride as per D1:Safe saving
    public String saveRide(Ride ride){

        //Check 1: Ride date cannot be in past
        if(ride.getRideDate() !=null && ride.getRideDate().isBefore(LocalDate.now())){
            return "Ride date cannot be in the past";
        }

        // Verify and attach  the corresponding steward to that rider if present
        Steward managingSteward=null;

        if(ride.getSteward() != null && ride.getSteward().getId() != null){
            Optional<Steward> steward=stewardRepository.findById(ride.getSteward().getId());
            if(steward.isPresent()){
                managingSteward=steward.get();
                ride.setSteward(managingSteward);
            }

        }

        //verify and attach the corresponding rider to this ride
        Rider theRider=null;
        if(ride.getRider() != null && ride.getRider().getId() != null){
            Optional< Rider> rider=riderRepository.findById(ride.getRider().getId());
            if(rider.isPresent()){
                theRider=rider.get();
                ride.setRider(theRider);
            }
        }

        // Rule/Constraint 2 from D1: Steward already has a ride booked on that date(whose status is not aborted)
        if(managingSteward != null && ride.getRideDate() != null){
            if(rideRepository.existsByStewardAndRideDateAndStatusNot(managingSteward,ride.getRideDate(),RideStatus.ABORTED)){
                return "Steward already has a ride booked on that date";
            }
        }

        // Rule 3: Always a ride starts as RESERVED status
        ride.setStatus(RideStatus.RESERVED);
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
//        This one is removed to maintain the D2 requirement that we must not update the status in general ride update.
//        existingRide.setStatus(updatedRide.getStatus());
        existingRide.setDestination(updatedRide.getDestination());
        existingRide.setDurationMinutes(updatedRide.getDurationMinutes());
        existingRide.setRider(updatedRide.getRider());
        existingRide.setSteward(updatedRide.getSteward());

        rideRepository.save(existingRide);

        return "Ride updated successfully";
    }

    // I updated delete to meet the requirement E3
    public String deleteRide(UUID id){
        Optional<Ride> ride=rideRepository.findById(id);

        if(ride.isEmpty()){
            return "The ride with that id does not exist";
        }
            Ride thisRide=ride.get();
        // E3: Check if ride has associated charges
        if(rideChargeRepository.existsByRide(thisRide)){
            return "That ride has ride charges, delete them first";
        }
        rideRepository.delete(thisRide);
        return "Ride deleted successfully";
    }

    // B2.Rides by status
    public List<Ride> getRidesByStatus(RideStatus status){
        return rideRepository.findByStatusOrderByRideDateAsc(status);
    }

    // B3. Rides in a date range
    public List<Ride> getRidesBetweenDates(LocalDate start,LocalDate end){
        return rideRepository.findByRideDateBetweenOrderByRideDateAsc(start,end);
    }

    // D2: Status flow, remember we commented/removed the updating ride status in general update to meet the status change flow as per PART D
    public String updateRideStatus(UUID id, RideStatus newStatus){
        Optional<Ride> ride=rideRepository.findById(id);
        if(ride.isEmpty()){
            return "The ride with that id does not exist";
        }

        Ride realRide=ride.get();
        RideStatus currentStatus=realRide.getStatus();

        boolean isUpdatingAllowed=false;

        if(currentStatus == RideStatus.RESERVED && (newStatus == RideStatus.UNLOCKED || newStatus == RideStatus.ABORTED)){
            isUpdatingAllowed = true;
        } else if (currentStatus == RideStatus.UNLOCKED && (newStatus == RideStatus.RETURNED || newStatus == RideStatus.ABORTED)) {
            isUpdatingAllowed = true;
        }

        if( !isUpdatingAllowed){
            return "Cannot change status from "+currentStatus+ " to "+newStatus;
        }
        realRide.setStatus(newStatus);
        rideRepository.save(realRide);
        return "Ride status updated successfully";
    }


}
