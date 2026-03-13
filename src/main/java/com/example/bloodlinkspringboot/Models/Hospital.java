package com.example.bloodlinkspringboot.Models;
import jakarta.persistence.*;

@Entity
@Table(name = "Hospital")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id")
    private Long hospitalId;

    @Column(name = "hospital_name")
    private String hospitalName;

    @Column(name = "hospital_pass")
    private String hospitalPass;

    @Column(name = "Hcontact_number")
    private String contactNumber;

    @Column(name = "Haddress")
    private String address;

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalPass() {
        return hospitalPass;
    }

    public void setHospitalPass(String hospitalPass) {
        this.hospitalPass = hospitalPass;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
