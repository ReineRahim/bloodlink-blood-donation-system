package com.example.bloodlinkspringboot.repository;

import com.example.bloodlinkspringboot.Models.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Optional<Hospital> findByHospitalNameAndHospitalPass(String hospitalName, String hospitalPass);
    Optional<Hospital> findByHospitalName(String hospitalName);

    boolean existsHospitalByHospitalId(Long hospitalId);
}
