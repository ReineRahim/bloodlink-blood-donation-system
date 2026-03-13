package com.example.bloodlinkspringboot.Models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Donations")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_id")
    private Long donationId;

    @Column(name = "donor_username")
    private String donorUsername;


    @Column(name = "blood_type")
    private BloodType bloodType;

    @Column(name = "volume_ml")
    private BigDecimal volumeMl;

    @Column(name = "donation_date")
    private LocalDateTime donationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.pending;

    public Long getDonationId() {
        return donationId;
    }

    public void setDonationId(Long donationId) {
        this.donationId = donationId;
    }

    public String getDonorUsername() {
        return donorUsername;
    }

    public void setDonorUsername(String donorUsername) {
        this.donorUsername = donorUsername;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public BigDecimal  getVolumeMl() {
        return volumeMl;
    }

    public void setVolumeMl(BigDecimal volumeMl) {
        this.volumeMl = volumeMl;
    }

    public LocalDateTime getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(LocalDateTime donationDate) {
        this.donationDate = donationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
