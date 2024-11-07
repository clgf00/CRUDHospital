package com.hospitalcrudapp.dao.repositories.JDBC;

import com.hospitalcrudapp.dao.model.MedRecord;
import com.hospitalcrudapp.dao.repositories.MedRecordRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
@Profile("active")
public class JDBCMedRecordRepository implements MedRecordRepository {
    private final DBConnectionPool dbConnection;


    public JDBCMedRecordRepository(DBConnectionPool dbConnection) {
        this.dbConnection = dbConnection;
    }


    @Override
    public List<MedRecord> getAll(int idPatient) {
        List<MedRecord> medicalRecords = new ArrayList<>();

        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SQLQueries.SELECT_MEDICAL_RECORDS_BY_PATIENT_ID)) {

            ps.setInt(1, idPatient);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MedRecord medRecord = new MedRecord();
                    medRecord.setId(rs.getInt("record_id"));
                    medRecord.setIdPatient(rs.getInt("patient_id"));
                    medRecord.setIdDoctor(Integer.parseInt(rs.getString("doctor_id")));
                    medRecord.setDescription(rs.getString("diagnosis"));
                    medRecord.setDate(rs.getDate("admission_date").toLocalDate());

                    medicalRecords.add(medRecord);
                }
            }

        } catch (SQLException sqle) {
            Logger.getLogger(JDBCPatientRepository.class.getName()).log(Level.SEVERE, null, sqle);
        }
        return medicalRecords;
    }

    @Override
    public int update(MedRecord medRecord) {
        int rowsAffected = 0;
        String updateMedicalRecordSQL = SQLQueries.UPDATE_MED_RECORD;

        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(updateMedicalRecordSQL)) {

            ps.setInt(1, medRecord.getIdDoctor());
            ps.setString(2, medRecord.getDescription());
            ps.setDate(3, java.sql.Date.valueOf(medRecord.getDate()));
            ps.setInt(4, medRecord.getId());

            rowsAffected = ps.executeUpdate();

        } catch (SQLException sqle) {
            Logger.getLogger(JDBCMedRecordRepository.class.getName()).log(Level.SEVERE, "Error updating medical record", sqle);
        }
        return rowsAffected;
    }
    @Override
    public void delete(int id) {
        String deleteMedicalRecordSQL = SQLQueries.DELETE_MED_RECORD;

        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(deleteMedicalRecordSQL)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }


    @Override
    public int add(MedRecord medRecord) {
        int rowsAffected = 0;
        String insertMedicalRecordSQL = SQLQueries.INSERT_INTO_MED_RECORDS;

        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(insertMedicalRecordSQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, medRecord.getIdPatient());
            ps.setInt(2, medRecord.getIdDoctor());
            ps.setString(3, medRecord.getDescription());
            ps.setDate(4, java.sql.Date.valueOf(medRecord.getDate()));

            rowsAffected = ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    medRecord.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException sqle) {
            Logger.getLogger(JDBCMedRecordRepository.class.getName()).log(Level.SEVERE, "Error inserting medical record", sqle);
        }
        return rowsAffected;
    }

}
