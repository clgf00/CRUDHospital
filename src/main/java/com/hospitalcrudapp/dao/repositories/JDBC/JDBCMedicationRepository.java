package com.hospitalcrudapp.dao.repositories.JDBC;

import com.hospitalcrudapp.dao.model.Medication;
import com.hospitalcrudapp.dao.repositories.MedicationRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
@Profile("active")
public class JDBCMedicationRepository implements MedicationRepository {

    private final DBConnectionPool dbConnection;

    public JDBCMedicationRepository(DBConnectionPool dbConnection) {
        this.dbConnection = dbConnection;
    }
    @Override
    public List<Medication> getAll() {
        List<Medication> medications = new ArrayList<>();
        String query = SQLQueries.GET_MEDS;

        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                Medication medication = Medication.builder()
                        .id(rs.getInt("medication_id"))
                        .medicationName(rs.getString("medication_name"))
                        .medRecordId(Integer.parseInt(rs.getString("record_id")))
                        .build();
                medications.add(medication);
            }
        } catch (SQLException e) {
            Logger.getLogger(JDBCMedicationRepository.class.getName()).log(Level.SEVERE, "Error fetching medications", e);
            throw new RuntimeException(e);
        }

        return medications;
    }
}
