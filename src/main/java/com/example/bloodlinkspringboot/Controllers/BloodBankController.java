package com.example.bloodlinkspringboot.Controllers;

import com.example.bloodlinkspringboot.Models.BloodBank;
import com.example.bloodlinkspringboot.Services.BloodBankServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bloodbanks")
public class BloodBankController {

    private final BloodBankServices bloodBankServices;

    @Autowired
    public BloodBankController(BloodBankServices bloodBankServices) {
        this.bloodBankServices = bloodBankServices;
    }

    // Create new BloodBank
    @PostMapping
    public ResponseEntity<BloodBank> createBloodBank(@RequestBody BloodBank bloodBank) {
        BloodBank saved = bloodBankServices.saveBloodBank(bloodBank);
        return ResponseEntity.ok(saved);
    }

    // Get all BloodBanks
    @GetMapping
    public ResponseEntity<List<BloodBank>> getAllBloodBanks() {
        List<BloodBank> list = bloodBankServices.getAllBloodBanks();
        return ResponseEntity.ok(list);
    }

    // Get BloodBank by ID
    @GetMapping("/{id}")
    public ResponseEntity<BloodBank> getBloodBankById(@PathVariable Long id) {
        Optional<BloodBank> bloodBankOpt = bloodBankServices.getBloodBankById(id);
        return bloodBankOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update BloodBank
    @PutMapping("/{id}")
    public ResponseEntity<BloodBank> updateBloodBank(@PathVariable Long id, @RequestBody BloodBank updatedBloodBank) {
        if (!id.equals(updatedBloodBank.getBloodBankId())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            BloodBank updated = bloodBankServices.updateBloodBank(updatedBloodBank);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete BloodBank
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBloodBank(@PathVariable Long id) {
        try {
            bloodBankServices.deleteBloodBank(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get BloodBank by Name (optional)
    @GetMapping("/search")
    public ResponseEntity<BloodBank> getBloodBankByName(@RequestParam String name) {
        BloodBank bloodBank = bloodBankServices.getBloodBankByName(name);
        if (bloodBank != null) {
            return ResponseEntity.ok(bloodBank);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
