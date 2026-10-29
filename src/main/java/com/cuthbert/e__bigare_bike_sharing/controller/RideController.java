package com.cuthbert.e__bigare_bike_sharing.controller;

import com.cuthbert.e__bigare_bike_sharing.model.Ride;
import com.cuthbert.e__bigare_bike_sharing.model.RideStatus;
import com.cuthbert.e__bigare_bike_sharing.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    @Autowired
    private RideService rideService;



    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Ride ride){
        String response=rideService.saveRide(ride);

        // I updated here to meet the D requirements
        if("Ride date cannot be in the past".equals(response)){

            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        if("Steward already has a ride booked on that date".equals(response)){
            return  new ResponseEntity<>(response,HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Ride>> getAll(){
        return new ResponseEntity<>(rideService.getAllRides(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>getById(@PathVariable UUID id){
        Optional<Ride> ride = rideService.getRideById(id);
        if (ride.isEmpty()) {
            return new ResponseEntity<>("The ride with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ride.get(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id,@RequestBody Ride ride){
        String response=rideService.updateRide(id,ride);

        if("The ride with that id does not exist".equals(response)){
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        String response=rideService.deleteRide(id);

        if ("The ride with that id does not exist".equals(response)) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        if("That ride has ride charges, delete them first".equals(response)){
            return new ResponseEntity<>(response,HttpStatus.CONFLICT);

        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    // B2. Rides by status
    @GetMapping("/by-status")
    public ResponseEntity<List<Ride>> getByStatus(@RequestParam RideStatus status) {
        List<Ride> rides = rideService.getRidesByStatus(status);
        return new ResponseEntity<>(rides, HttpStatus.OK);
    }

    // B3. Rides in a date range
    @GetMapping("/between")
    public ResponseEntity<List<Ride>>getRidesBetween(@RequestParam String start,@RequestParam String end){
        LocalDate startDate=LocalDate.parse(start);
        LocalDate endDate=LocalDate.parse(end);

        List<Ride> rides=rideService.getRidesBetweenDates(startDate,endDate);
        return new ResponseEntity<>(rides,HttpStatus.OK);
    }

    // D2: PATCH /api/rides/{id}/status?status=UNLOCKED and here I went for PATCH because we are updating only 1 column in the table
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable UUID id,@RequestParam RideStatus status){
        String message=rideService.updateRideStatus(id,status);

        if("The ride with that id does not exist".equals(message)){
            return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
        }
        if(message.startsWith("Cannot change status from")){
            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message,HttpStatus.OK);
    }




}
