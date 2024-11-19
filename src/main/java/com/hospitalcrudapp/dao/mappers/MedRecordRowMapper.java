package com.hospitalcrudapp.dao.mappers;

import com.hospitalcrudapp.dao.model.MedRecord;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class MedRecordRowMapper implements RowMapper<MedRecord> {
    @Override
    public MedRecord mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        int id = resultSet.getInt("record_id");
        int patientId = resultSet.getInt("patient_id");
        int doctorId = resultSet.getInt("doctor_id");
        String diagnosis = resultSet.getString("diagnosis");
        LocalDate admissionDate = resultSet.getDate("admission_date").toLocalDate(); // Columna admission_date

        MedRecord medRecord = new MedRecord();
        medRecord.setId(id);
        medRecord.setIdPatient(patientId);
        medRecord.setIdDoctor(doctorId);
        medRecord.setDescription(diagnosis);
        medRecord.setDate(admissionDate);

        return medRecord;
    }
}