package com.cuthbert.e__bigare_bike_sharing.controller;

import com.cuthbert.e__bigare_bike_sharing.model.Ride;
import com.cuthbert.e__bigare_bike_sharing.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


}
