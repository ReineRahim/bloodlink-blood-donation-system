package com.example.bloodlinkspringboot.Models;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "BloodBank")
public class BloodBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blood_bank_id")
    private Long bloodBankId;

    @Column(name = "Bname")
    private String name;

    @Column(name = "Blocation")
    private String location;

    @Column(name = "Bcontact_number")
    private String contactNumber;

    @Column(name = "capacity_liters")
    private BigDecimal capacityLiters;


    public Long getBloodBankId() {
        return bloodBankId;
    }

    public void setBloodBankId(Long bloodBankId) {
        this.bloodBankId = bloodBankId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public BigDecimal getCapacityLiters() {
        return capacityLiters;
    }

    public void setCapacityLiters(BigDecimal capacityLiters) {
        this.capacityLiters = capacityLiters;
    }
}
