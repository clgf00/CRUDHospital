package com.hospitalcrudapp.domain.services;

import com.hospitalcrudapp.dao.model.Doctor;
import com.hospitalcrudapp.dao.repositories.JPA.JPADoctorRepository;
import com.hospitalcrudapp.domain.model.DoctorUi;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {
    private final JPADoctorRepository doctorRepository;

    public DoctorService(JPADoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;

    }
    public List<DoctorUi> findAll() {
        List<Doctor> doctors = doctorRepository.getAll();
        List<DoctorUi> doctorUi = new ArrayList<>();
        doctors.forEach(doctor ->
                doctorUi.add(new DoctorUi(doctor.getDoctor_id(), doctor.getName())));
        return doctorUi;
    }
}