package com.hospitalcrudapp.dao.repositories;

import com.hospitalcrudapp.dao.model.Patient;

import java.util.List;


public interface PatientRepository {
    List<Patient> getAll();

    int add(Patient patient);

    void update(Patient patient);

    void delete(int id, boolean confirm);
}
