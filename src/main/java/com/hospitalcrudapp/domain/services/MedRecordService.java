package com.hospitalcrudapp.domain.services;

import com.hospitalcrudapp.dao.model.Doctor;
import com.hospitalcrudapp.dao.model.MedRecord;
import com.hospitalcrudapp.dao.model.Medication;
import com.hospitalcrudapp.dao.model.Patient;
import com.hospitalcrudapp.dao.repositories.JPA.JPAMedRecordRepository;
import com.hospitalcrudapp.domain.model.MedRecordUi;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Service
public class MedRecordService {
    private final JPAMedRecordRepository medRecordRepository;

    public MedRecordService(JPAMedRecordRepository medRecordRepository) {
        this.medRecordRepository = medRecordRepository;
    }

    public List<MedRecordUi> getAll(int idPatient) {
        List<MedRecord> medRecords = medRecordRepository.getAll(idPatient);
        List<MedRecordUi> medRecordUi = new ArrayList<>();

        for (MedRecord medRecord : medRecords) {
            medRecordUi.add(new MedRecordUi(
                    medRecord.getId(),
                    medRecord.getPatient().getId(),
                    medRecord.getDoctor().getDoctor_id(),
                    medRecord.getDescription(),
                    medRecord.getDate(),
                    medRecord.getMedications()
                            .stream()
                            .map(Medication::getMedicationName)
                            .toList()));
        }
        return medRecordUi;
    }
    public int add(MedRecordUi medRecordUi) {

        Patient patient = medRecordRepository.getPatientById(medRecordUi.getIdPatient());
        Doctor doctor = medRecordRepository.getDoctorById(medRecordUi.getIdDoctor());

        List<Medication> medications = new ArrayList<>();
        for (String medicationName : medRecordUi.getMedications()) {
            Medication medication = new Medication();
            medication.setMedicationName(medicationName);
            medications.add(medication);
        }

        MedRecord medRecord = new MedRecord(
                medRecordUi.getId(),
                patient,
                doctor,
                medRecordUi.getDescription(),
                medRecordUi.getDate(),
                medications
        );

        medications.forEach(medication -> medication.setMedRecord(medRecord));
        return medRecordRepository.add(medRecord);
    }


    public void update(MedRecordUi medRecordUi) {
        Patient patient = medRecordRepository.getPatientById(medRecordUi.getIdPatient());
        Doctor doctor = medRecordRepository.getDoctorById(medRecordUi.getIdDoctor());

        List<Medication> medications = new ArrayList<>();
        for (String medicationName : medRecordUi.getMedications()) {
            Medication medication = new Medication();
            medication.setMedicationName(medicationName);
            medications.add(medication);
        }

        MedRecord medRecord = new MedRecord(
                medRecordUi.getId(),
                patient,
                doctor,
                medRecordUi.getDescription(),
                medRecordUi.getDate(),
                medications
        );

        medications.forEach(medication -> medication.setMedRecord(medRecord));
        medRecordRepository.update(medRecord);
    }

    public void delete(int id) throws Exception {
        medRecordRepository.delete(id);
    }
}
