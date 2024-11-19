package com.hospitalcrudapp.dao.repositories.SpringJDBC;

import com.hospitalcrudapp.dao.mappers.PatientRowMapper;
import com.hospitalcrudapp.dao.model.Patient;
import com.hospitalcrudapp.dao.repositories.CredentialRepository;
import com.hospitalcrudapp.dao.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Repository
@Component
public class SpringPatientRepository implements PatientRepository {
    @Autowired
    private JdbcClient jdbcClient;
    private final PatientRowMapper patientRowMapper;
    private final CredentialRepository credentialRepository;

    public SpringPatientRepository(PatientRowMapper patientRowMapper, CredentialRepository credentialRepository) {
        this.patientRowMapper = patientRowMapper;
        this.credentialRepository = credentialRepository;
    }

    @Override
    public List<Patient> getAll() {
        return jdbcClient.sql("select * from patients")
                .query(patientRowMapper)
                .list();

    }

    @Override
    @Transactional
    public int add(Patient patient) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql("insert into patients (name, date_of_birth, phone) values (?,?,?);")
                .param(1, patient.getName())
                .param(2, patient.getBirthDate())
                .param(3, patient.getPhoneNumber())
                .update(keyHolder);

        int newId = Objects.requireNonNull(keyHolder.getKey(), "Key was not generated").intValue();
        patient.setId(newId);
        patient.getCredentials().setPatientId(newId);
        credentialRepository.add(patient.getCredentials());

        return newId;
    }

    @Override
    public void update(Patient patient) {
        jdbcClient.sql("update patients set name = ?, date_of_birth = ?, phone = ?, where patient_id = :id")
                .param(1, patient.getName())
                .param("dob", patient.getBirthDate())
                .param("phone", patient.getPhoneNumber())
                .param("id", patient.getId());
    }

    @Override
    @Transactional
    public int delete(int id, boolean confirm) {
        jdbcClient.sql("DELETE FROM prescribed_medications WHERE record_id IN (SELECT record_id FROM medical_records WHERE patient_id = ?)")
                .param(1, id)
                .update();

        jdbcClient.sql("DELETE FROM medical_records WHERE patient_id = ?")
                .param(1, id)
                .update();

        jdbcClient.sql("delete from user_login where patient_id = ?")
                .param(1, id)
                .update();
        return jdbcClient.sql("delete from patients where patient_id = ?")
                .param(1, id)
                .update();
    }
}
