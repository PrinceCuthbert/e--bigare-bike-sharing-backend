package com.cuthbert.e__bigare_bike_sharing.controller;

import com.cuthbert.e__bigare_bike_sharing.model.Steward;
import com.cuthbert.e__bigare_bike_sharing.service.StewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/stewards")
public class StewardController {

    @Autowired
    private StewardService stewardService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Steward steward){
        String response=stewardService.saveSteward(steward);
        if ("Steward phone already exists".equals(response)) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Steward>>getAll(){
        return new ResponseEntity<>(stewardService.getAllStewards(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>getStewardById(@PathVariable UUID id){
        Optional<Steward> steward=stewardService.getStewardById(id);

        if(steward.isEmpty()){
            return new ResponseEntity<>("The steward with that id does not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(steward.get(),HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String>update(@PathVariable UUID id,@RequestBody Steward steward){
        String message=stewardService.updatedSteward(id,steward);

        if ("The steward with that id does not exist".equals(message)) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if ("Steward phone already exists".equals(message)){
            return new ResponseEntity<>(message,HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        String response=stewardService.deleteSteward(id);
        if("The steward with that id does not exist".equals(response)){
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    // C1: Stewards by bike type
    @GetMapping("/by-bike-type")
    public ResponseEntity<List<Steward>> getByBikeType(@RequestParam String name){
        return  new ResponseEntity<>(stewardService.getStewardsByBikeType(name),HttpStatus.OK);
    }

    // D3: PUT /api/stewards/{stewardId}/bike-types/{bikeTypeId}

    @PutMapping("/{stewardId}/bike-types/{bikeTypeId}")
    public ResponseEntity<String> addBikeType(@PathVariable UUID stewardId,@PathVariable UUID bikeTypeId){
        String message=stewardService.addBikeTypeToSteward(stewardId,bikeTypeId);
        if ("The steward with that id does not exist".equals(message) || "That bike type does not exist".equals(message)) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if ("The steward already has that bike type".equals(message)) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message,HttpStatus.OK);

    }

    // D4: PATCH /api/stewards/{stewardId}/station?stationNumber=4
    @PatchMapping("/{stewardId}/station")
    public ResponseEntity<String> moveStation(@PathVariable UUID stewardId, @RequestParam int stationNumber) {
     String message=stewardService.moveStewardToStation(stewardId,stationNumber);
     if("The steward with that id does not exist".equals(message) || "The station with that stationNumber does not exist".equals(message)){
         return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
     }
     if("That station is taken by another steward".equals(message)){
         return new ResponseEntity<>(message,HttpStatus.CONFLICT);
     }
     return new ResponseEntity<>(message,HttpStatus.OK);
    }

}
