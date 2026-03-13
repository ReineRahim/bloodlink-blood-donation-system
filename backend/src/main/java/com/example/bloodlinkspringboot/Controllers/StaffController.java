package com.example.bloodlinkspringboot.Controllers;

import com.example.bloodlinkspringboot.Models.Staff;
import com.example.bloodlinkspringboot.Services.StaffServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StaffController {

    @Autowired
    private StaffServices staffServices;

    @GetMapping("/all")
    public List<Staff> getAllStaff() {
        return staffServices.getAllStaff();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable Long id) {
        Optional<Staff> staff = staffServices.getStaffById(id);
        return staff.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/create")
    public ResponseEntity<Staff> createStaff(@RequestBody Staff staff) {
        Staff createdStaff = staffServices.saveStaff(staff);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStaff);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Staff> updateStaff(@PathVariable Long id, @RequestBody Staff staff) {
        Staff updatedStaff = staffServices.updateStaff(id, staff);
        if (updatedStaff == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedStaff);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        Optional<Staff> staff = staffServices.getStaffById(id);
        if (staff.isPresent()) {
            staffServices.deleteStaff(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PostMapping("/login")
    public ResponseEntity<String> loginStaff(@RequestParam String username,
                                             @RequestParam String role,
                                             @RequestParam String staff_password) {
        System.out.println("USERNAME: " + username);
        System.out.println("ROLE: " + role);
        System.out.println("PASSWORD: " + staff_password);

        boolean authenticated = staffServices.checkLogin(username, role, staff_password);
        if (authenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
