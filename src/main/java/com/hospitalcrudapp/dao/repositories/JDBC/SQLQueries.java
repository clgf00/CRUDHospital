package com.hospitalcrudapp.dao.repositories.JDBC;

public class SQLQueries {

    public static final String SELECT_PATIENTS_QUERY = "SELECT p.patient_id, p.name, p.date_of_birth, p.phone, u.username, u.password " +
            "FROM patients p " +
            "INNER JOIN user_login u ON p.patient_id = u.patient_id";

    public static final String SELECT_MEDICAL_RECORDS_BY_PATIENT_ID =
            "SELECT record_id, patient_id, doctor_id, diagnosis, admission_date FROM medical_records WHERE patient_id = ?";
    public static final String INSERT_INTO_MED_RECORDS = "INSERT INTO medical_records (patient_id, doctor_id, diagnosis, admission_date) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_TOTAL_PATIENT = "UPDATE patients SET name = ?, date_of_birth = ?, phone = ? WHERE patient_id = ?";
    public static final String ADD_PATIENT = "INSERT INTO patients (name, date_of_birth, phone) VALUES (?, ?, ?)";
    public static final String DELETE_PATIENT = "DELETE FROM patients WHERE patient_id = ?";
    public static final String TOTAL_PAYMENTS_QUERY = "SELECT SUM(amount) FROM patient_payments WHERE patient_id = ? GROUP BY patient_id";
    public static final String INSERT_USER_LOGIN = "INSERT INTO user_login (username, password, patient_id) VALUES (?, ?, ?)";
    public static final String DELETE_USER_LOGIN = "DELETE FROM user_login WHERE patient_id = ?";
    public static final String DELETE_MED_RECORD = "DELETE FROM medical_records WHERE record_id = ?";
    public static final String UPDATE_MED_RECORD = "UPDATE medical_records SET doctor_id = ?, diagnosis = ?, admission_date = ? WHERE record_id = ?";
    public static final String INSERT_INTO_PRESCRIBED_MEDICATIONS =
            "INSERT INTO prescribed_medications (record_id, medication_name) VALUES (?, ?)";
    public static final String DELETE_MEDICATIONS = "DELETE FROM prescribed_medications WHERE record_id = ?";
    public static final String SELECT_MEDICATIONS_BY_RECORD_ID =
            "SELECT medication_name FROM prescribed_medications WHERE record_id = ?";
    public static final String GET_MEDS = "SELECT prescription_id, record_id, medication_name, dosage FROM prescribed_medications";
    public static final String SELECT_DOCTORS_QUERY = "SELECT doctor_id, name, specialization FROM doctors";
    public static final String GET_CREDENTIALS = "select * from user_login where username = ?";
    public static final String ADD_CREDENTIALS = "insert into user_login (username, password) values (?,?)";
    public static final String GET_DOCTORS = "select * from doctors";
    public static final String GET_MEDICATIONS = "SELECT prescription_id, medication_name, record_id FROM prescribed_medications WHERE record_id = ?";
    public static final String GET_MEDRECORDS = "SELECT record_id, patient_id, doctor_id, diagnosis, admission_date FROM medical_records WHERE patient_id = ?";
    public static final String UPDATE_MEDRECORD = "UPDATE medical_records SET doctor_id = ?, diagnosis = ?, admission_date = ? WHERE record_id = ?";
    public static final String UPDATE_MEDICATIONS = "UPDATE prescribed_medications SET medication_name = ? WHERE record_id = ? AND prescription_id = ?";
    public static final String ADD_MEDICATIONS = "INSERT INTO prescribed_medications (record_id, medication_name) VALUES (?, ?)";
    public static final String UPDATE_PATIENT = "UPDATE patients SET name = ?, date_of_birth = ?, phone = ? WHERE patient_id = ?";
    public static final String GET_PATIENTS = "select * from patients";
    public static final String DELETE_CREDENTIALS_PATIENT = "delete from user_login where patient_id = ?";
    public static final String SUM_PAYMENTS = "SELECT SUM(amount) FROM patient_payments WHERE patient_id = ?";
    public static final String DELETE_PAYMENTS = "DELETE FROM patient_payments WHERE patient_id = ?";
    public static final String DELETE_APPOINTMENTS = "DELETE FROM appointments WHERE patient_id = ?";
    public static final String DELETE_MEDR = "DELETE FROM medical_records WHERE patient_id = ?";
    public static final String DELETE_MEDS = "DELETE pm FROM prescribed_medications pm JOIN medical_records mr ON pm.record_id = mr.record_id WHERE mr.patient_id=?";




}

