package com.example.bloodlinkspringboot.Services;

import com.example.bloodlinkspringboot.Models.Staff;
import com.example.bloodlinkspringboot.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffServices {

    private final StaffRepository staffRepository;

    @Autowired
    public StaffServices(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }


    // Create or update a staff member
    public Staff saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    // Retrieve all staff members
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    // Retrieve a staff member by ID
    public Optional<Staff> getStaffById(Long staffId) {
        return staffRepository.findById(staffId);
    }

    // Update staff by ID
    public Staff updateStaff(Long staffId, Staff updatedStaff) {
        return staffRepository.findById(staffId).map(existingStaff -> {
            existingStaff.setFirstName(updatedStaff.getFirstName());
            existingStaff.setLastName(updatedStaff.getLastName());
            existingStaff.setRole(updatedStaff.getRole());
            existingStaff.setStaffPass(updatedStaff.getStaffPass()); // Consider hashing in real apps
            return staffRepository.save(existingStaff);
        }).orElse(null);
    }

    // Delete a staff member by ID
    public void deleteStaff(Long staffId) {
        staffRepository.deleteById(staffId);
    }

    // Check login credentials
    public boolean checkLogin(String username, String role, String password) {
        Staff staff = staffRepository.findByStaffUsernameAndRole(username, role);
        return staff != null && staff.getStaffPass().equals(password);
    }
}



