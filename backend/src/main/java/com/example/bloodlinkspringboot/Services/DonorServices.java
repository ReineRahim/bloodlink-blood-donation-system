package com.example.bloodlinkspringboot.Services;

import com.example.bloodlinkspringboot.Models.Donation;
import com.example.bloodlinkspringboot.Models.Donor;
import com.example.bloodlinkspringboot.repository.DonationRepository;
import com.example.bloodlinkspringboot.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonorServices {

    private final DonorRepository donorRepository;

    @Autowired
    public DonorServices(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    // Retrieve all donors
    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    // Create new donor if username is unique
    public Donor createDonor(Donor donor) {
        if (donorRepository.existsByUsername(donor.getUsername())) {
            throw new IllegalArgumentException("Username already exists.");
        }
        return donorRepository.save(donor);
    }


    // Authenticate donor by username and password
    public boolean authenticateDonor(String username, String password) {
        Donor donor = donorRepository.findByUsername(username);
        return donor != null && donor.getPassword().equals(password);
    }

    // Get donor by username
    public Donor getDonorByUsername(String username) {
        return donorRepository.findByUsername(username);
    }

    // Get donor by ID
    public Optional<Donor> getDonorById(Long donorId) {
        return donorRepository.findById(donorId);
    }

    // Update donor by ID
    public Donor updateDonor(Long id, Donor updatedDonor) {
        Donor existing = donorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Donor with ID " + id + " not found."));

        if (!existing.getUsername().equals(updatedDonor.getUsername()) &&
                donorRepository.existsByUsername(updatedDonor.getUsername())) {
            throw new IllegalArgumentException("Username already taken.");
        }

        existing.setDfirstName(updatedDonor.getDfirstName());
        existing.setDlastName(updatedDonor.getDlastName());
        existing.setUsername(updatedDonor.getUsername());
        existing.setPassword(updatedDonor.getPassword());
        existing.setGender(updatedDonor.getGender());
        existing.setDaddress(updatedDonor.getDaddress());
        existing.setDcontactNumber(updatedDonor.getDcontactNumber());
        existing.setDateOfBirth(updatedDonor.getDateOfBirth());

        return donorRepository.save(existing);
    }


    // Delete donor by ID
    public void deleteDonor(Long id) {
        if (!donorRepository.existsById(id)) {
            throw new IllegalArgumentException("Donor with ID " + id + " not found.");
        }
        donorRepository.deleteById(id);
    }


    // Delete donor by username
    public void deleteDonorByUsername(String username) {
        donorRepository.deleteByUsername(username);
    }

    // Custom check and create method
    public boolean checkAndCreateDonor(Donor donor) {
        if (donorRepository.existsByUsername(donor.getUsername())) {
            return false;
        }

        try {
            donorRepository.save(donor);
            return true;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return false;
        }
    }

}

