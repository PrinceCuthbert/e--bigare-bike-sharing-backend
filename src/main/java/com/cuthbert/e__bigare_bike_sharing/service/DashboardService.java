package com.cuthbert.e__bigare_bike_sharing.service;

import com.cuthbert.e__bigare_bike_sharing.model.RideStatus;
import com.cuthbert.e__bigare_bike_sharing.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {
    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private StewardRepository stewardRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
   private RideRepository rideRepository;

    @Autowired
    private RideChargeRepository rideChargeRepository;

    public Map<String,Long> getSummary(){
        Map<String,Long> summary=new LinkedHashMap<>();

        summary.put("riders", riderRepository.count());
        summary.put("stewards",stewardRepository.count());
        summary.put("freeStations",(long)stationRepository.findFreeStations().size());
        summary.put("ridesToday",rideRepository.countByRideDate(LocalDate.now()));
        summary.put("reserved",rideRepository.countByStatus(RideStatus.RESERVED));
        summary.put("rideCharges",rideChargeRepository.count());

        return summary;
    }
}
