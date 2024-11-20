package com.hospitalcrudapp.dao.repositories.SpringJDBC;


import com.hospitalcrudapp.dao.mappers.MedRecordRowMapper;
import com.hospitalcrudapp.dao.mappers.MedicationRowMapper;
import com.hospitalcrudapp.dao.model.MedRecord;
import com.hospitalcrudapp.dao.model.Medication;
import com.hospitalcrudapp.dao.repositories.JDBC.SQLQueries;
import com.hospitalcrudapp.dao.repositories.MedRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@Profile("active")
public class SpringMedRecordRepository implements MedRecordRepository {
    @Autowired
    private JdbcClient jdbcClient;
    @Autowired
    private MedRecordRowMapper medRecordRowMapper;
    @Autowired
    private MedicationRowMapper medicationRowMapper;

    @Override
    public List<MedRecord> getAll(int idPatient) {
        List<MedRecord> records = jdbcClient.sql(SQLQueries.GET_MEDRECORDS)
                .param(1, idPatient)
                .query(medRecordRowMapper)
                .list();
        for (MedRecord medRecord : records) {
            List<Medication> medications = jdbcClient.sql(SQLQueries.GET_MEDICATIONS)
                    .param(1, medRecord.getId())
                    .query(medicationRowMapper)
                    .list();

            medRecord.setMedications(medications);
        }

        return records;
    }


    @Override
    public int update(MedRecord medRecord) {
        int updatedMedicalRecords = jdbcClient.sql(SQLQueries.UPDATE_MEDRECORD)
                .param(1, medRecord.getIdDoctor())
                .param(2, medRecord.getDescription())
                .param(3, medRecord.getDate())
                .param(4, medRecord.getId())
                .update();

        String prescriptionIds = medRecord.getMedications().stream()
                .map(medication -> String.valueOf(medication.getId()))
                .collect(Collectors.joining(","));

        jdbcClient.sql(SQLQueries.DELETE_MEDICATIONS)
                .param(1, medRecord.getId())
                .update();

        int updatedMedications = 0;
        for (Medication medication : medRecord.getMedications()) {
            int rowsAffected = jdbcClient.sql(SQLQueries.UPDATE_MEDICATIONS)
                    .param(1, medication.getMedicationName())
                    .param(2, medRecord.getId())
                    .param(3, medication.getId())
                    .update();

            if (rowsAffected == 0) {
                updatedMedications += jdbcClient.sql(SQLQueries.ADD_MEDICATIONS)
                        .param(1, medRecord.getId())
                        .param(2, medication.getMedicationName())
                        .update();
            } else {
                updatedMedications++;
            }
        }

        return updatedMedicalRecords + updatedMedications;
    }


    @Override
    public void delete(int id) {

        jdbcClient.sql(SQLQueries.DELETE_MEDICATIONS)
                .param(1, id)
                .update();

        jdbcClient.sql(SQLQueries.DELETE_MED_RECORD)
                .param(1, id)
                .update();


    }

    @Override
    public int add(MedRecord medRecord) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql("INSERT INTO medical_records (patient_id, doctor_id, diagnosis, admission_date) VALUES (?, ?, ?, ?)")
                .param(1, medRecord.getIdPatient())
                .param(2, medRecord.getIdDoctor())
                .param(3, medRecord.getDescription())
                .param(4, medRecord.getDate())
                .update(keyHolder);

        int newId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        medRecord.setId(newId);

        if (medRecord.getMedications() != null && !medRecord.getMedications().isEmpty()) {
            for (Medication medication : medRecord.getMedications()) {
                jdbcClient.sql("INSERT INTO prescribed_medications (record_id, medication_name) VALUES (?, ?)")
                        .param(1, newId)
                        .param(2, medication.getMedicationName())
                        .update();
            }
        }
        return newId;
    }
}
