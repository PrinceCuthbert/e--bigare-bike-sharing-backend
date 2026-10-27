package com.cuthbert.e__bigare_bike_sharing.controller;

import com.cuthbert.e__bigare_bike_sharing.model.BikeType;
import com.cuthbert.e__bigare_bike_sharing.service.BikeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/bike-types")
public class BikeTypeController {

    @Autowired
    private BikeTypeService bikeTypeService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody BikeType bikeType){
        String message=bikeTypeService.saveBikeType(bikeType);
        if("Bike type already exists".equals(message)){
            return new ResponseEntity<>(message,HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BikeType>> getAllBikeTypes(){
        return new ResponseEntity<>(bikeTypeService.getAllBikeTypes(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<BikeType> bikeType = bikeTypeService.getBikeTypeById(id);
        if (bikeType.isEmpty()) {
            return new ResponseEntity<>("The bike type with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bikeType.get(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody BikeType bikeType) {
        String msg = bikeTypeService.updateBikeType(id, bikeType);
        if ("The bike type with that id does not exist".equals(msg)) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        if("Bike type already exists".equals(msg)){
            return new ResponseEntity<>(msg,HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String msg = bikeTypeService.deleteBikeType(id);
        if ("The bike type with that id does not exist".equals(msg)) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
