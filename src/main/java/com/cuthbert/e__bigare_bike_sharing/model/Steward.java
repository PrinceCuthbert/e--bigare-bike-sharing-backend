package com.cuthbert.e__bigare_bike_sharing.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="steward")
public class Steward {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;

    private String lastName;

    private String phone;

    @ManyToOne
    @JoinColumn(name="station_id",nullable = false)
    private Station station;

    @ManyToMany
    @JoinTable(
            name = "steward_bike_type",
            joinColumns = @JoinColumn(name="steward_id"),
            inverseJoinColumns = @JoinColumn(name="bike_type_id")
    )
    private List<BikeType> bikeTypes=new ArrayList<>();

    public Steward() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public List<BikeType> getBikeTypes() {
        return bikeTypes;
    }

    public void setBikeTypes(List<BikeType> bikeTypes) {
        this.bikeTypes = bikeTypes;
    }
}
