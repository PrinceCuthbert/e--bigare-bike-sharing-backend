package com.cuthbert.e__bigare_bike_sharing.controller;


import com.cuthbert.e__bigare_bike_sharing.model.RideCharge;
import com.cuthbert.e__bigare_bike_sharing.service.RideChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ride-charges")
public class RideChargeController {

    @Autowired
    private RideChargeService rideChargeService;

    // E1: POST /api/ride-charges/save?rideId=...
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam UUID rideId, @RequestBody RideCharge charge){
        String message=rideChargeService.saveRideCharge(rideId,charge);

        if("The ride with that id does not exist".equals(message)){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if("Ride charges are only for RETURNED rides".equals(message)){
            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message,HttpStatus.CREATED);
    }

    // E2: GET /api/ride-charges/rider/{riderCode}
    @GetMapping("/rider/{riderCode}")
    public  ResponseEntity<List<RideCharge>> getByRider(@PathVariable String riderCode){
        List<RideCharge> charges=rideChargeService.getChargesByRiderCode(riderCode);
        return new ResponseEntity<>(charges,HttpStatus.OK);
    }

    // E3: DELETE /api/ride-charges/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        String response=rideChargeService.deleteRideCharge(id);
        if ("The ride charge with that id does not exist".equals(response)) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


}
