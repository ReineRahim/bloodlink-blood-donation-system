package com.example.bloodlinkspringboot.repository;

import com.example.bloodlinkspringboot.Models.Donation;
import com.example.bloodlinkspringboot.Models.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findAllDonationsByDonorUsername(String username);
    Donation getDonationByDonorUsername(String username);


}