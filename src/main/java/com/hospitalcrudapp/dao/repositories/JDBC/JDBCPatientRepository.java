package com.hospitalcrudapp.dao.repositories.JDBC;

import com.hospitalcrudapp.dao.model.Credential;
import com.hospitalcrudapp.dao.model.Patient;
import com.hospitalcrudapp.dao.repositories.PatientRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
@Profile("active")
public class JDBCPatientRepository implements PatientRepository {

    private final DBConnection dbConnection;

    public JDBCPatientRepository(DBConnection connection) {
        this.dbConnection = connection;
    }

    public List<Patient> getAll() {
        List<Patient> patients = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_PATIENTS_QUERY);

            while (rs.next()) {
                int id = rs.getInt("patient_id");
                String name = rs.getString("name");
                LocalDate birthDate = rs.getDate("date_of_birth").toLocalDate();
                String phoneNumber = rs.getString("phone");


                //TODO HACER EN EL MAPPER
                Patient patient = new Patient(id, name, birthDate, phoneNumber, null);
                patients.add(patient);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return patients;
    }

    @Override
    public int add(Patient patient) {
        int rowsAffected = 0;
        String insertPatientSQL = SQLQueries.INSERT_PATIENT;
        String insertUserLoginSQL = SQLQueries.INSERT_USER_LOGIN;

        try (Connection con = dbConnection.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement ps = con.prepareStatement(insertPatientSQL, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, patient.getName());
                ps.setDate(2, java.sql.Date.valueOf(patient.getBirthDate()));
                ps.setString(3, patient.getPhoneNumber());
                rowsAffected += ps.executeUpdate();

                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int patientId = generatedKeys.getInt(1);

                    try (PreparedStatement userLoginPs = con.prepareStatement(insertUserLoginSQL)) {
                        userLoginPs.setString(1, patient.getCredentials().getUsername());
                        userLoginPs.setString(2, patient.getCredentials().getPassword());
                        userLoginPs.setInt(3, patientId);
                        rowsAffected += userLoginPs.executeUpdate();
                    }
                }
            }
            con.commit();
        } catch (SQLException sqle) {
            //todo añadir rollback
            //todo añadir finally
            Logger.getLogger(JDBCPatientRepository.class.getName()).log(Level.SEVERE, null, sqle);
        }
        return rowsAffected;
    }
    @Override
    public void update(Patient patient) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_TOTAL_PATIENT)) {
            preparedStatement.setString(1, patient.getName());
            preparedStatement.setString(2, String.valueOf(patient.getBirthDate()));
            preparedStatement.setString(3, patient.getPhoneNumber());
            preparedStatement.setInt(4, patient.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException sqle) {
            Logger.getLogger(JDBCPatientRepository.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }

    @Override
    public int delete(int id, boolean confirm) {
        int rowsAffected = 0;
        Connection con = null;

        try {
            con = dbConnection.getConnection();
            con.setAutoCommit(false);
            //TODO dejar todo en un solo try catch
            try (PreparedStatement deleteUserLogin = con.prepareStatement(SQLQueries.DELETE_USER_LOGIN);
                 PreparedStatement deletePatient = con.prepareStatement(SQLQueries.DELETE_PATIENT)) {

                deleteUserLogin.setInt(1, id);
                deleteUserLogin.executeUpdate();


                deletePatient.setInt(1, id);
                rowsAffected = deletePatient.executeUpdate();

                con.commit();
            } catch (SQLException e) {
                con.rollback();
                Logger.getLogger(JDBCPatientRepository.class.getName()).log(Level.SEVERE, null, e);
            }
        } catch (SQLException sqle) {
            Logger.getLogger(JDBCPatientRepository.class.getName()).log(Level.SEVERE, null, sqle);
        }
        return rowsAffected;
    }
    public double getTotalPayments(int patientId) {
        double totalPayments = 0.0;
        try (Connection con = dbConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQLQueries.TOTAL_PAYMENTS_QUERY)) {

            pstmt.setInt(1, patientId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                totalPayments = rs.getDouble(1);
            }
        } catch (SQLException sqle) {
            Logger.getLogger(JDBCPatientRepository.class.getName()).log(Level.SEVERE, null, sqle);
        }
        return totalPayments;
    }
}