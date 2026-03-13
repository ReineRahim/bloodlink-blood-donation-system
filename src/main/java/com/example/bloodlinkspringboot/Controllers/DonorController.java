package com.example.bloodlinkspringboot.Controllers;

import com.example.bloodlinkspringboot.Models.Donor;
import com.example.bloodlinkspringboot.Services.DonorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/donors")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DonorController {

    @Autowired
    private DonorServices donorServices;

    @GetMapping("/all")
    public List<Donor> getAllDonors() {
        return donorServices.getAllDonors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donor> getDonorById(@PathVariable Long id) {
        Optional<Donor> donor = donorServices.getDonorById(id);
        return donor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Donor> getDonorByUsername(@PathVariable String username) {
        Donor donor = donorServices.getDonorByUsername(username);
        if (donor != null) {
            return ResponseEntity.ok(donor);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDonor(@RequestBody Donor donor) {
        Donor createdDonor = donorServices.createDonor(donor);
        if (createdDonor == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDonor);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Donor> updateDonor(@PathVariable Long id, @RequestBody Donor donor) {
        Donor updatedDonor = donorServices.updateDonor(id, donor);
        if (updatedDonor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedDonor);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDonor(@PathVariable Long id) {
        Optional<Donor> donor = donorServices.getDonorById(id);
        if (donor.isPresent()) {
            donorServices.deleteDonor(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/deleteByUsername/{username}")
    public ResponseEntity<Void> deleteDonorByUsername(@PathVariable String username) {
        Donor donor = donorServices.getDonorByUsername(username);
        if (donor != null) {
            donorServices.deleteDonorByUsername(username);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginDonor(@RequestParam String username, @RequestParam String password) {
        boolean authenticated = donorServices.authenticateDonor(username, password);
        if (authenticated) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    @PostMapping("/checkAndCreate")
    public ResponseEntity<String> checkAndCreateDonor(@RequestBody Donor donor) {
        boolean created = donorServices.checkAndCreateDonor(donor);
        if (created) {
            return ResponseEntity.ok("Donor created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists or invalid data");
        }
    }
}
