package com.example.bloodlinkspringboot.repository;

import com.example.bloodlinkspringboot.Models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

   Staff findByStaffUsernameAndRole( String username, String role);
}
