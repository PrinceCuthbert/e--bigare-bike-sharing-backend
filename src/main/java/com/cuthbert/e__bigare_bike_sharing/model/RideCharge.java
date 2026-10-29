package com.cuthbert.e__bigare_bike_sharing.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "ride_charge")
public class RideCharge {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String chargeType;
    private String note;
    private int amount;
    private LocalDate chargedDate;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;

    public RideCharge() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDate getChargedDate() {
        return chargedDate;
    }

    public void setChargedDate(LocalDate chargedDate) {
        this.chargedDate = chargedDate;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }
}
