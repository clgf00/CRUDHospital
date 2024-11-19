package com.hospitalcrudapp.dao.repositories.JDBC;

import com.hospitalcrudapp.dao.model.Doctor;
import com.hospitalcrudapp.dao.repositories.DoctorRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

@Repository
@Profile("active")
public class JDBCDoctorRepository implements DoctorRepository {

    private final DBConnectionPool dbConnection;

    public JDBCDoctorRepository(DBConnectionPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Doctor> getAll() {
        List<Doctor> doctors = new ArrayList<>();
        String query = SQLQueries.SELECT_DOCTORS_QUERY;
        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                Doctor doctor = Doctor.builder()
                        .doctor_id(rs.getInt("doctor_id"))
                        .name(rs.getString("name"))
                        .specialty(rs.getString("specialization"))
                        .build();
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            Logger.getLogger(JDBCDoctorRepository.class.getName()).log(Level.SEVERE, "Error fetching doctors", e);
            throw new RuntimeException(e);
        }

        return doctors;
    }
}
