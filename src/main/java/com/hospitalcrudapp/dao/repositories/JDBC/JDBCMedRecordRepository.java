package com.hospitalcrudapp.dao.repositories.JDBC;

import com.hospitalcrudapp.dao.model.MedRecord;
import com.hospitalcrudapp.dao.model.Medication;
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

        // Consulta para obtener los registros médicos
        String selectMedicationsSQL = SQLQueries.SELECT_MEDICATIONS_BY_RECORD_ID;

        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SQLQueries.SELECT_MEDICAL_RECORDS_BY_PATIENT_ID)) {

            // Configuramos el id del paciente en la consulta de registros médicos
            ps.setInt(1, idPatient);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Recuperamos un MedRecord
                    MedRecord medRecord = new MedRecord();
                    medRecord.setId(rs.getInt("record_id"));
                    medRecord.setIdPatient(rs.getInt("patient_id"));
                    medRecord.setIdDoctor(rs.getInt("doctor_id"));
                    medRecord.setDescription(rs.getString("diagnosis"));
                    medRecord.setDate(rs.getDate("admission_date").toLocalDate());

                    // Recuperamos las medicaciones asociadas a este MedRecord
                    try (PreparedStatement psMedications = con.prepareStatement(selectMedicationsSQL)) {
                        psMedications.setInt(1, medRecord.getId());

                        try (ResultSet rsMedications = psMedications.executeQuery()) {
                            List<Medication> medications = new ArrayList<>();
                            while (rsMedications.next()) {
                                Medication medication = new Medication();
                                medication.setMedicationName(rsMedications.getString("medication_name"));
                                medications.add(medication);
                            }
                            medRecord.setMedications(medications);  // Establecemos las medicaciones en el MedRecord
                        }
                    }

                    medicalRecords.add(medRecord);
                }
            }

        } catch (SQLException sqle) {
            Logger.getLogger(JDBCPatientRepository.class.getName()).log(Level.SEVERE, null, sqle);
        }

        return medicalRecords;  // Devolvemos la lista completa de registros médicos
    }



    @Override
    public int update(MedRecord medRecord) {
        int rowsAffected = 0;
        String updateMedicalRecordSQL = SQLQueries.UPDATE_MED_RECORD;
        String deleteMedicationsSQL = SQLQueries.DELETE_MEDICATIONS;
        String insertMedicationSQL = SQLQueries.INSERT_INTO_PRESCRIBED_MEDICATIONS;

        try (Connection con = dbConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement psUpdateMedRecord = con.prepareStatement(updateMedicalRecordSQL);
                 PreparedStatement psDeleteMedications = con.prepareStatement(deleteMedicationsSQL);
                 PreparedStatement psInsertMedication = con.prepareStatement(insertMedicationSQL)) {

                psUpdateMedRecord.setInt(1, medRecord.getIdDoctor());
                psUpdateMedRecord.setString(2, medRecord.getDescription());
                psUpdateMedRecord.setDate(3, java.sql.Date.valueOf(medRecord.getDate()));
                psUpdateMedRecord.setInt(4, medRecord.getId());
                rowsAffected = psUpdateMedRecord.executeUpdate();

                psDeleteMedications.setInt(1, medRecord.getId());
                psDeleteMedications.executeUpdate();

                for (Medication medication : medRecord.getMedications()) {
                    psInsertMedication.setInt(1, medRecord.getId());
                    psInsertMedication.setString(2, medication.getMedicationName());
                    psInsertMedication.addBatch();
                }

                psInsertMedication.executeBatch();
                con.commit();

            } catch (SQLException sqle) {
                con.rollback();
                Logger.getLogger(JDBCMedRecordRepository.class.getName()).log(Level.SEVERE, "Error updating medical record and medications", sqle);
            }
        } catch (SQLException sqle) {
            Logger.getLogger(JDBCMedRecordRepository.class.getName()).log(Level.SEVERE, "Error with DB connection", sqle);
        }
        return rowsAffected;
    }
    @Override
    public void delete(int id) {
        String deleteMedicationSQL = SQLQueries.DELETE_MEDICATIONS;
        String deleteMedicalRecordSQL = SQLQueries.DELETE_MED_RECORD;

        try (Connection con = dbConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement psDeleteMedication = con.prepareStatement(deleteMedicationSQL);
                 PreparedStatement psDeleteMedRecord = con.prepareStatement(deleteMedicalRecordSQL)) {

                psDeleteMedication.setInt(1, id);
                psDeleteMedication.executeUpdate();

                psDeleteMedRecord.setInt(1, id);
                psDeleteMedRecord.executeUpdate();

                con.commit();

            } catch (SQLException sqle) {
                con.rollback();
                throw new RuntimeException("Error deleting medical record and medications", sqle);
            }
        } catch (SQLException sqle) {
            throw new RuntimeException("Error with DB connection or statement", sqle);
        }
    }


    @Override
    public int add(MedRecord medRecord) {
        int rowsAffected = 0;
        String insertMedicalRecordSQL = SQLQueries.INSERT_INTO_MED_RECORDS;
        String insertMedicationSQL = SQLQueries.INSERT_INTO_PRESCRIBED_MEDICATIONS;

        try (Connection con = dbConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement psMedRecord = con.prepareStatement(insertMedicalRecordSQL, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement psMedication = con.prepareStatement(insertMedicationSQL)) {

                psMedRecord.setInt(1, medRecord.getIdPatient());
                psMedRecord.setInt(2, medRecord.getIdDoctor());
                psMedRecord.setString(3, medRecord.getDescription());
                psMedRecord.setDate(4, java.sql.Date.valueOf(medRecord.getDate()));
                rowsAffected = psMedRecord.executeUpdate();

                try (ResultSet generatedKeys = psMedRecord.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int recordId = generatedKeys.getInt(1);

                        for (Medication medication : medRecord.getMedications()) {
                            psMedication.setInt(1, recordId);
                            psMedication.setString(2, medication.getMedicationName());
                            psMedication.addBatch();
                        }

                        psMedication.executeBatch();
                    }
                }
                con.commit();

            } catch (SQLException sqle) {
                con.rollback();
                Logger.getLogger(JDBCMedRecordRepository.class.getName()).log(Level.SEVERE, "Error inserting medical record or medications", sqle);
            }

        } catch (SQLException sqle) {
            Logger.getLogger(JDBCMedRecordRepository.class.getName()).log(Level.SEVERE, "Error with DB connection or statement", sqle);
        }

        return rowsAffected;
    }




}
