package com.example.bloodlinkspringboot.repository;

import com.example.bloodlinkspringboot.Models.BloodBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodBankRepository extends JpaRepository<BloodBank, Long> {
    BloodBank findByName(String name);
    void deleteById(Long id);
}
