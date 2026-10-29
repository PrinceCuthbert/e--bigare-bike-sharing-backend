package com.cuthbert.e__bigare_bike_sharing.controller;

import com.cuthbert.e__bigare_bike_sharing.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;


    // Bonus 1: Dashboard summary
    @GetMapping("/summary")
    public ResponseEntity<?> getSummary(){
        return new ResponseEntity<>(dashboardService.getSummary(), HttpStatus.OK);
    }

}
