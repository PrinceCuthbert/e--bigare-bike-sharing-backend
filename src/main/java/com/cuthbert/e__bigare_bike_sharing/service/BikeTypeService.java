package com.cuthbert.e__bigare_bike_sharing.service;

import com.cuthbert.e__bigare_bike_sharing.model.BikeType;
import com.cuthbert.e__bigare_bike_sharing.repository.BikeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BikeTypeService {

    @Autowired
    private BikeTypeRepository bikeTypeRepository;

    public String saveBikeType(BikeType bikeType){
        bikeTypeRepository.save(bikeType);

        return "Bike type saved successfully";
    }

    public List<BikeType> getAllBikeTypes(){
        return bikeTypeRepository.findAll();
    }

    public Optional<BikeType>getBikeTypeById(UUID id){
        return bikeTypeRepository.findById(id);
    }

    public String updateBikeType(UUID id,BikeType updatedBikeType){
        Optional<BikeType> bikeType=bikeTypeRepository.findById(id);

        if(bikeType.isEmpty()){
            return "The bike type with that id does not exist";
        }

        BikeType existing=bikeType.get();
        existing.setName(updatedBikeType.getName());
        bikeTypeRepository.save(existing);
        return "Bike type updated successfully";
    }

    public String deleteBikeType(UUID id){
        Optional<BikeType> bikeType=bikeTypeRepository.findById(id);
        if(bikeType.isEmpty()){
            return "The bike type with that id does not exist";
        }
        bikeTypeRepository.delete(bikeType.get());
        return "Bike type deleted successfully";
    }


}
