package com.hospitalcrudapp.domain.services;

import com.hospitalcrudapp.dao.model.Credential;
import com.hospitalcrudapp.dao.model.Patient;
import com.hospitalcrudapp.dao.repositories.JPA.JPAPatientRepository;
import com.hospitalcrudapp.domain.model.PatientUi;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    private final JPAPatientRepository patientRepository;

    public PatientService(JPAPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientUi> getPatients() {
        List<Patient> patients = patientRepository.getAll();
        List<PatientUi> patientui = new ArrayList<>();
        patients.forEach(patient -> patientui.add(new PatientUi(
                patient.getId(),
                patient.getName(),
                patient.getBirthDate(),
                patient.getPhoneNumber(),
                null,
                null,
                0)));
        return patientui;
    }

    public int addPatient(PatientUi patientui) {
        Credential credential = new Credential(patientui.getUserName(), patientui.getPassword(), patientui.getId());
        Patient patient = new Patient(patientui.getId(), patientui.getName(), patientui.getBirthDate(), patientui.getPhone(), credential);
        return patientRepository.add(patient);
    }

    public void updatePatient(PatientUi patientui) {
        Patient patient = new Patient(patientui.getId(), patientui.getName(), patientui.getBirthDate(), patientui.getPhone(), new Credential(patientui.getUserName(), patientui.getPassword(), patientui.getId()));
        patientRepository.update(patient);
    }

    public void deletePatient(int id, boolean confirm) {
        patientRepository.delete(id, confirm);
    }
}