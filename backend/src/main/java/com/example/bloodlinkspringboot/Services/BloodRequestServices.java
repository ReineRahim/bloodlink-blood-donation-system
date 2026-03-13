package com.example.bloodlinkspringboot.Services;

import com.example.bloodlinkspringboot.Models.BloodRequest;
import com.example.bloodlinkspringboot.Models.BloodType;
import com.example.bloodlinkspringboot.Models.Status;
import com.example.bloodlinkspringboot.repository.BloodRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BloodRequestServices {

    private final BloodRequestRepository bloodRequestRepository;

    @Autowired
    public BloodRequestServices(BloodRequestRepository bloodRequestRepository) {
        this.bloodRequestRepository = bloodRequestRepository;
    }

    // Save a new blood request
    public BloodRequest saveBloodRequest(BloodRequest request) {
        return bloodRequestRepository.save(request);
    }

    // Get all blood requests
    public List<BloodRequest> getAllBloodRequests() {
        return bloodRequestRepository.findAll();
    }

    // Get a single blood request by ID
    public Optional<BloodRequest> getBloodRequestById(Long id) {
        return bloodRequestRepository.findById(id);
    }

    // Get all requests for a specific hospital
    public List<BloodRequest> getRequestsByHospitalName(String hospitalName) {
        return bloodRequestRepository.findAllByHospitalName(hospitalName);
    }

    // Update blood request status by ID (example implementation)
    public BloodRequest updateBloodRequestStatus(Long id, String status) {
        Optional<BloodRequest> optionalRequest = bloodRequestRepository.findById(id);
        if (optionalRequest.isPresent()) {
            BloodRequest request = optionalRequest.get();
            try {
                request.setStatus(Status.valueOf(status.toLowerCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status value: " + status);
            }
            return bloodRequestRepository.save(request);
        } else {
            throw new IllegalArgumentException("BloodRequest with ID " + id + " not found.");
        }
    }

    // Update an existing blood request (full update)
    public BloodRequest updateBloodRequest(BloodRequest updatedRequest) {
        Long id = updatedRequest.getRequestId();
        if (id == null || !bloodRequestRepository.existsById(id)) {
            throw new IllegalArgumentException("BloodRequest with ID " + id + " not found.");
        }
        return bloodRequestRepository.save(updatedRequest);
    }

    // Delete a blood request by ID
    public void deleteBloodRequestById(Long id) {
        if (bloodRequestRepository.existsById(id)) {
            bloodRequestRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("BloodRequest with ID " + id + " not found.");
        }
    }
}
