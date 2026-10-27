package com.cuthbert.e__bigare_bike_sharing.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="ride")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate rideDate;

    @Enumerated(EnumType.STRING)
    private RideStatus status;

    private String destination;

    private Integer durationMinutes;

    @ManyToOne
    @JoinColumn(name = "rider_id")
    private Rider rider;

    @ManyToOne
    @JoinColumn(name="steward_id")
    private Steward steward;

    public Ride() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getRideDate() {
        return rideDate;
    }

    public void setRideDate(LocalDate rideDate) {
        this.rideDate = rideDate;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public Steward getSteward() {
        return steward;
    }

    public void setSteward(Steward steward) {
        this.steward = steward;
    }
}
