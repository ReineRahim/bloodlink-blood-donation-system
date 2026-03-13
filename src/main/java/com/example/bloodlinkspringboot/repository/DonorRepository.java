package com.example.bloodlinkspringboot.repository;

import com.example.bloodlinkspringboot.Models.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
    Donor findByUsername(String username);
    boolean existsByUsername(String username);
    void deleteByUsername(String username);
}
