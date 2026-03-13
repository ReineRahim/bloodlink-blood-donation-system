package com.example.bloodlinkspringboot.repository;

import com.example.bloodlinkspringboot.Models.BloodRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {
    List<BloodRequest> findAllByHospitalName(String Hname);
}
