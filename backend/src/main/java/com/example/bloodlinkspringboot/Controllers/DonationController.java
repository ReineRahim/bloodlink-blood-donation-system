package com.example.bloodlinkspringboot.Controllers;

import com.example.bloodlinkspringboot.Models.Donation;
import com.example.bloodlinkspringboot.Services.DonationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    private final DonationServices donationServices;

    @Autowired
    public DonationController(DonationServices donationServices) {
        this.donationServices = donationServices;
    }

    // Create a new donation
    @PostMapping
    public ResponseEntity<Donation> createDonation(@RequestBody Donation donation) {
        Donation savedDonation = donationServices.saveDonation(donation);
        return ResponseEntity.ok(savedDonation);
    }

    // Get all donations
    @GetMapping
    public ResponseEntity<List<Donation>> getAllDonations() {
        List<Donation> donations = donationServices.getAllDonations();
        return ResponseEntity.ok(donations);
    }

    // Get donation by ID
    @GetMapping("/{id}")
    public ResponseEntity<Donation> getDonationById(@PathVariable Long id) {
        Optional<Donation> donation = donationServices.getDonationById(id);
        return donation.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all donations by donor username (query param)
    @GetMapping("/byDonor")
    public ResponseEntity<List<Donation>> getDonationsByDonorUsername(@RequestParam String username) {
        List<Donation> donations = donationServices.getDonationsByUsername(username);
        return ResponseEntity.ok(donations);
    }

    // Get single donation by donor username (if expecting only one)
    @GetMapping("/singleByDonor")
    public ResponseEntity<Donation> getDonationByDonorUsername(@RequestParam String username) {
        Donation donation = donationServices.getDonationByDonorUsername(username);
        if (donation != null) {
            return ResponseEntity.ok(donation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update an entire donation by ID
    @PutMapping("/{id}")
    public ResponseEntity<Donation> updateDonation(@PathVariable Long id, @RequestBody Donation updatedDonation) {
        if (!id.equals(updatedDonation.getDonationId())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Donation savedDonation = donationServices.updateDonation(updatedDonation);
            return ResponseEntity.ok(savedDonation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Update only donation status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateDonationStatus(@PathVariable Long id, @RequestBody String newStatus) {
        try {
            donationServices.updateDonationStatus(id, newStatus);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a donation by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonation(@PathVariable Long id) {
        try {
            donationServices.deleteDonation(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
