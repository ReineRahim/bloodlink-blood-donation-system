package com.example.bloodlinkspringboot.Services;

import com.example.bloodlinkspringboot.Models.BloodBank;
import com.example.bloodlinkspringboot.repository.BloodBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BloodBankServices {

    private  BloodBankRepository bloodBankRepository;

    @Autowired
    public BloodBankServices(BloodBankRepository bloodBankRepository) {
        this.bloodBankRepository = bloodBankRepository;
    }

    // Save a new BloodBank
    public BloodBank saveBloodBank(BloodBank bloodBank) {
        return bloodBankRepository.save(bloodBank);
    }

    // Retrieve all BloodBanks
    public List<BloodBank> getAllBloodBanks() {
        return bloodBankRepository.findAll();
    }

    // Retrieve a BloodBank by its ID
    public Optional<BloodBank> getBloodBankById(Long id) {
        return bloodBankRepository.findById(id);
    }

    // Update a BloodBank
    public BloodBank updateBloodBank(BloodBank updatedBloodBank) {
        if (bloodBankRepository.existsById(updatedBloodBank.getBloodBankId())) {
            return bloodBankRepository.save(updatedBloodBank);
        } else {
            throw new RuntimeException("BloodBank with ID " + updatedBloodBank.getBloodBankId() + " not found.");
        }
    }

    // Delete a BloodBank
    public void deleteBloodBank(Long id) {
        if (bloodBankRepository.existsById(id)) {
            bloodBankRepository.deleteById(id);
        } else {
            throw new RuntimeException("BloodBank with ID " + id + " not found.");
        }
    }

    // Find BloodBank by name
    public BloodBank getBloodBankByName(String name) {
        return bloodBankRepository.findByName(name);
    }
}
