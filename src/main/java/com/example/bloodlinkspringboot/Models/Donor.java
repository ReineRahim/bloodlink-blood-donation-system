package com.example.bloodlinkspringboot.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "Donor")
public class Donor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donor_id")
    private Long donorId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "Dfirst_name")
    private String DfirstName;

    @Column(name = "Dlast_name")
    private String DlastName;

    @Column(name = "Dcontact_number")
    private String DcontactNumber;

    @Column(name = "Daddress")
    private String Daddress;

    @Column(name = "gender")
    private String gender;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    public Long getDonorId() {
        return donorId;
    }

    public void setDonorId(Long donorId) {
        this.donorId = donorId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDfirstName() {
        return DfirstName;
    }

    public void setDfirstName(String dfirstName) {
        DfirstName = dfirstName;
    }

    public String getDlastName() {
        return DlastName;
    }

    public void setDlastName(String dlastName) {
        DlastName = dlastName;
    }

    public String getDcontactNumber() {
        return DcontactNumber;
    }

    public void setDcontactNumber(String dcontactNumber) {
        DcontactNumber = dcontactNumber;
    }

    public String getDaddress() {
        return Daddress;
    }

    public void setDaddress(String daddress) {
        Daddress = daddress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}

