package com.hospitalcrudapp.dao.repositories.JDBC;

public class SQLQueries {

    public static final String SELECT_PATIENTS_QUERY = "SELECT p.patient_id, p.name, p.date_of_birth, p.phone, u.username, u.password " +
            "FROM patients p " +
            "INNER JOIN user_login u ON p.patient_id = u.patient_id";
    public static final String UPDATE_TOTAL_PATIENT = "UPDATE patients SET name = ?, date_of_birth = ?, phone = ? WHERE patient_id = ?";
    public static final String INSERT_PATIENT = "INSERT INTO patients (name, date_of_birth, phone) VALUES (?, ?, ?)";
    public static final String INSERT_PATIENT_WITH_AUTOINCREMENTAL_ID = "INSERT INTO patients (patient_id, name, date_of_birth, phone) VALUES (?, ?, ?, ?)";
    public static final String DELETE_PATIENT = "DELETE FROM patients WHERE patient_id = ?";
    public static final String DELETE_PATIENT_MEDICAL_RECORDS = "DELETE FROM medical_records WHERE patient_id = ?";
    public static final String TOTAL_PAYMENTS_QUERY = "SELECT SUM(amount) FROM patient_payments WHERE patient_id = ?";
    public static final String INSERT_USER_LOGIN = "INSERT INTO user_login (username, password, patient_id) VALUES (?, ?, ?)";
    public static final String DELETE_USER_LOGIN = "DELETE FROM user_login WHERE patient_id = ?";

}