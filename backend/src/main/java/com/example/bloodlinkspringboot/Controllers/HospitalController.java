package com.example.bloodlinkspringboot.Controllers;

import com.example.bloodlinkspringboot.Models.Hospital;
import com.example.bloodlinkspringboot.Services.HospitalServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    private final HospitalServices hospitalServices;

    @Autowired
    public HospitalController(HospitalServices hospitalServices) {
        this.hospitalServices = hospitalServices;
    }

    // Create new hospital
    @PostMapping
    public ResponseEntity<Hospital> createHospital(@RequestBody Hospital hospital) {
        Hospital savedHospital = hospitalServices.saveHospital(hospital);
        return ResponseEntity.ok(savedHospital);
    }

    // Get all hospitals
    @GetMapping
    public ResponseEntity<List<Hospital>> getAllHospitals() {
        List<Hospital> hospitals = hospitalServices.getAllHospitals();
        return ResponseEntity.ok(hospitals);
    }

    // Get hospital by ID
    @GetMapping("/{id}")
    public ResponseEntity<Hospital> getHospitalById(@PathVariable Long id) {
        Optional<Hospital> hospitalOpt = hospitalServices.getHospitalById(id);
        return hospitalOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update hospital
    @PutMapping("/{id}")
    public ResponseEntity<Hospital> updateHospital(@PathVariable Long id, @RequestBody Hospital updatedHospital) {
        if (!id.equals(updatedHospital.getHospitalId())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Hospital hospital = hospitalServices.updateHospital(updatedHospital);
            return ResponseEntity.ok(hospital);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete hospital
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospital(@PathVariable Long id) {
        try {
            hospitalServices.deleteHospital(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Hospital login check
    @PostMapping("/login")
    public ResponseEntity<String> checkLogin(@RequestParam String hospitalName, @RequestParam String hospitalPass) {
        boolean authenticated = hospitalServices.checkHospitalLogin(hospitalName, hospitalPass);
        if (authenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
