package com.example.bloodlinkspringboot.Controllers;

import com.example.bloodlinkspringboot.Models.BloodRequest;
import com.example.bloodlinkspringboot.Services.BloodRequestServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bloodrequests")
public class BloodRequestController {

    private final BloodRequestServices bloodRequestServices;

    @Autowired
    public BloodRequestController(BloodRequestServices bloodRequestServices) {
        this.bloodRequestServices = bloodRequestServices;
    }

    // Create a new blood request
    @PostMapping
    public ResponseEntity<BloodRequest> createBloodRequest(@RequestBody BloodRequest bloodRequest) {
        BloodRequest savedRequest = bloodRequestServices.saveBloodRequest(bloodRequest);
        return ResponseEntity.ok(savedRequest);
    }

    // Get all blood requests
    @GetMapping
    public ResponseEntity<List<BloodRequest>> getAllBloodRequests() {
        List<BloodRequest> requests = bloodRequestServices.getAllBloodRequests();
        return ResponseEntity.ok(requests);
    }

    // Get blood request by ID
    @GetMapping("/{id}")
    public ResponseEntity<BloodRequest> getBloodRequestById(@PathVariable Long id) {
        Optional<BloodRequest> request = bloodRequestServices.getBloodRequestById(id);
        return request.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all requests for a specific hospital by hospitalName query param
    @GetMapping("/byHospital")
    public ResponseEntity<List<BloodRequest>> getRequestsByHospitalName(@RequestParam String hospitalName) {
        List<BloodRequest> requests = bloodRequestServices.getRequestsByHospitalName(hospitalName);
        return ResponseEntity.ok(requests);
    }

    // Update entire blood request
    @PutMapping("/{id}")
    public ResponseEntity<BloodRequest> updateBloodRequest(@PathVariable Long id, @RequestBody BloodRequest updatedRequest) {
        if (!id.equals(updatedRequest.getRequestId())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            BloodRequest savedRequest = bloodRequestServices.updateBloodRequest(updatedRequest);
            return ResponseEntity.ok(savedRequest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Update blood request status only
    @PatchMapping("/{id}/status")
    public ResponseEntity<BloodRequest> updateBloodRequestStatus(@PathVariable Long id, @RequestBody String status) {
        try {
            BloodRequest updated = bloodRequestServices.updateBloodRequestStatus(id, status);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a blood request
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBloodRequest(@PathVariable Long id) {
        try {
            bloodRequestServices.deleteBloodRequestById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
