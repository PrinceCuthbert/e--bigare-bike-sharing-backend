package com.cuthbert.e__bigare_bike_sharing.controller;

import com.cuthbert.e__bigare_bike_sharing.model.Station;
import com.cuthbert.e__bigare_bike_sharing.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/stations")
public class StationController {

    @Autowired
    private StationService stationService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Station station){
        String response=stationService.saveStation(station);
        if("Station number already exists".equals(response)){
            return new ResponseEntity<>(response,HttpStatus.CONFLICT);
        }

        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Station>> getAll(){
        return new ResponseEntity<>(stationService.getAllStations(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Station> station = stationService.getStationById(id);
        if (station.isEmpty()) {
            return new ResponseEntity<>("The station with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(station.get(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Station station) {
        String resp = stationService.updateStation(id, station);
        if ("The station with that id does not exist".equals(resp)) {
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }
        if("Station number already exists".equals(resp)){
            return new ResponseEntity<>(resp,HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        String responseMessage=stationService.deleteStation(id);

        if("The station with that id does not exist".equals(responseMessage)){
            return new ResponseEntity<>(responseMessage,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }
}
