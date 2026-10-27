package com.cuthbert.e__bigare_bike_sharing.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="station")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int stationNumber;

    private String name;

    public Station() {
    }

    public Station(int stationNumber, String name) {
        this.stationNumber = stationNumber;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
