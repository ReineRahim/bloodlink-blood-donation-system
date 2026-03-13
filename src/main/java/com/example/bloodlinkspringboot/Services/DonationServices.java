package com.example.bloodlinkspringboot.Services;

import com.example.bloodlinkspringboot.Models.Donation;
import com.example.bloodlinkspringboot.Models.Status;
import com.example.bloodlinkspringboot.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationServices {

    private final DonationRepository donationRepository;

    @Autowired
    public DonationServices(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    // Save a new donation
    public Donation saveDonation(Donation donation) {
        return donationRepository.save(donation);
    }

    // Retrieve all donations
    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    // Retrieve donation by ID
    public Optional<Donation> getDonationById(Long donationId) {
        return donationRepository.findById(donationId);
    }

    // Update an existing donation
    public Donation updateDonation(Donation updatedDonation) {
        if (donationRepository.existsById(updatedDonation.getDonationId())) {
            return donationRepository.save(updatedDonation);
        } else {
            throw new RuntimeException("Donation with ID " + updatedDonation.getDonationId() + " not found.");
        }
    }

    // Update only the status of a donation
    public void updateDonationStatus(Long donationId, String newStatus) {
        Optional<Donation> donationOptional = donationRepository.findById(donationId);
        if (donationOptional.isPresent()) {
            Donation donation = donationOptional.get();
            try {
                donation.setStatus(Status.valueOf(newStatus.toLowerCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status value: " + newStatus);
            }
            donationRepository.save(donation);
        } else {
            throw new RuntimeException("Donation with ID " + donationId + " not found.");
        }
    }

    // Delete a donation by ID
    public void deleteDonation(Long donationId) {
        if (donationRepository.existsById(donationId)) {
            donationRepository.deleteById(donationId);
        } else {
            throw new RuntimeException("Donation with ID " + donationId + " not found.");
        }
    }

    // Get all donations by donor username
    public List<Donation> getDonationsByUsername(String username) {
        return donationRepository.findAllDonationsByDonorUsername(username);
    }

    // Get single donation by donor username
    public Donation getDonationByDonorUsername(String username) {
        return donationRepository.getDonationByDonorUsername(username);
    }
}
