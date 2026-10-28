package com.cuthbert.e__bigare_bike_sharing.controller;

import com.cuthbert.e__bigare_bike_sharing.model.Rider;
import com.cuthbert.e__bigare_bike_sharing.service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/riders")
public class RiderController {

    @Autowired
    private RiderService riderService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Rider rider){
        String response=riderService.saveRider(rider);
        if("Rider code already exists".equals(response) || "Rider phone already exists".equals(response) ){
            return new ResponseEntity<>(response,HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Rider>> getAll(){
        return new ResponseEntity<>(riderService.getAllRiders(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>getById(@PathVariable UUID id){
        Optional<Rider> rider=riderService.getRiderById(id);

        if(rider.isEmpty()){
            return new ResponseEntity<>("The rider with that id does not exist",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rider.get(),HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id,@RequestBody Rider rider){
        String message=riderService.updatedRider(id, rider);

        if ("The rider with that id does not exist".equals(message)) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if ("Rider code already exists".equals(message) || "Rider phone already exists".equals(message)) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        String response=riderService.deleteRider(id);

        if ("The rider with that id does not exist".equals(response)) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    // B1. Find riders by lastName
    @GetMapping("/by-last-name")
    public ResponseEntity<List<Rider>>getByLastName(@RequestParam String lastName){
        List<Rider> riders=riderService.getRidersByLastName(lastName);

        return new ResponseEntity<>(riders,HttpStatus.OK);
    }

    // C4: Riders of a stewards (nukuvuga all riders associated with a certain steward)
    @GetMapping("/of-steward/{stewardId}")
    public ResponseEntity<?> getRidersOfSteward(@PathVariable UUID stewardId){
        Optional<List<Rider>> riders=riderService.getRidersOfSteward(stewardId);

        if(riders.isEmpty()){
            return new ResponseEntity<>("The steward with that id does not exist",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(riders.get(),HttpStatus.OK);
    }
}
