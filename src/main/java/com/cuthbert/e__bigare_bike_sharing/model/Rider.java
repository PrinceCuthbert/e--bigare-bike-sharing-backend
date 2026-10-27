package com.cuthbert.e__bigare_bike_sharing.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "rider")
public class Rider {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;

    private String lastName;

    @Column(nullable = false,unique = true)
    private String riderCode;

    private String phone;

    public Rider() {
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

    public String getRiderCode() {
        return riderCode;
    }

    public void setRiderCode(String riderCode) {
        this.riderCode = riderCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
