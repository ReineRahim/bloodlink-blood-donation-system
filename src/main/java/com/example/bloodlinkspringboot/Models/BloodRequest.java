package com.example.bloodlinkspringboot.Models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "BloodRequest")
public class BloodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "hospital_name")
    private String hospitalName;

    @Column(name = "staff_name")
    private String staffName;

    // @Enumerated(EnumType.STRING)
    @Column(name = "blood_type_needed")
    private BloodType bloodTypeNeeded;

    @Column(name = "required_volume")
    private BigDecimal requiredVolume;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_of_request")
    private Date dateOfRequest = new Date();

    // Getters and setters

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public BloodType getBloodTypeNeeded() {
        return bloodTypeNeeded;
    }

    public void setBloodTypeNeeded(BloodType bloodTypeNeeded) {
        this.bloodTypeNeeded = bloodTypeNeeded;
    }

    public BigDecimal getRequiredVolume() {
        return requiredVolume;
    }

    public void setRequiredVolume(BigDecimal requiredVolume) {
        this.requiredVolume = requiredVolume;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(Date dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }
}
