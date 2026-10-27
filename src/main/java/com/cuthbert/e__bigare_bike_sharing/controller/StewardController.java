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
}
