package com.cuthbert.e__bigare_bike_sharing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="bike_type")
public class BikeType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "bikeTypes")
    private List<Steward> stewards=new ArrayList<>();

    public BikeType(){

    }

    public BikeType(String name) {
        this.name = name;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Steward> getStewards() {
        return stewards;
    }

    public void setStewards(List<Steward> stewards) {
        this.stewards = stewards;
    }
}
