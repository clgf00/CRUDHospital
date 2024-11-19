package com.hospitalcrudapp.dao.mappers;

import com.hospitalcrudapp.dao.model.Credential;
import com.hospitalcrudapp.dao.model.Patient;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class PatientRowMapper implements RowMapper<Patient> {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Patient mapRow2(String s) throws SQLException {
        String[] parts = s.split(";");
        if (parts.length >= 4) {
            int id = Integer.parseInt(parts[0].trim());
            String name = parts[1].trim();
            LocalDate birthDate = LocalDate.parse(parts[2].trim(), formatter);
            String phoneNumber = parts[3].trim();
            return new Patient(id, name, birthDate, phoneNumber);
        }
        return null;
    }
    @Override
    public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
        Patient patient = new Patient();
        patient.setId(rs.getInt("patient_id"));
        patient.setName(rs.getString("name"));
        patient.setBirthDate(rs.getDate("date_of_birth").toLocalDate());
        patient.setPhoneNumber(rs.getString("phone"));

        Credential credentials = new Credential();

        if (columnExists(rs, "username")) {
            credentials.setUsername(rs.getString("username"));
        }


        if (columnExists(rs, "password")) {
            credentials.setPassword(rs.getString("password"));
        }

        patient.setCredentials(credentials);
        return patient;
    }

    private boolean columnExists(ResultSet rs, String columnName) {
        try {
            rs.findColumn(columnName);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}

