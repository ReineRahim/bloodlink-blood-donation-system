package com.example.bloodlinkspringboot.Services;

import com.example.bloodlinkspringboot.Models.Hospital;
import com.example.bloodlinkspringboot.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalServices {

    private final HospitalRepository hospitalRepository;

    @Autowired
    public HospitalServices(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    public Hospital saveHospital(Hospital hospital) {
        if (hospitalRepository.existsHospitalByHospitalId(hospital.getHospitalId())){
            throw new IllegalArgumentException("Hospital name already exists.");
        }
        return hospitalRepository.save(hospital);
    }


    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public Optional<Hospital> getHospitalById(Long id) {
        return hospitalRepository.findById(id);
    }

    public Hospital updateHospital(Hospital updatedHospital) {
        if (hospitalRepository.existsById(updatedHospital.getHospitalId())) {
            return hospitalRepository.save(updatedHospital);
        } else {
            throw new IllegalArgumentException("Hospital with ID " + updatedHospital.getHospitalId() + " not found.");
        }
    }

    public void deleteHospital(Long id) {
        if (hospitalRepository.existsById(id)) {
            hospitalRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Hospital with ID " + id + " not found.");
        }
    }

    public boolean checkHospitalLogin(String hospitalName, String hospitalPass) {
        return hospitalRepository.findByHospitalNameAndHospitalPass(hospitalName, hospitalPass).isPresent();
    }
}
