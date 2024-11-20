package com.hospitalcrudapp.dao.repositories.SpringJDBC;

import com.hospitalcrudapp.dao.mappers.PatientRowMapper;
import com.hospitalcrudapp.dao.model.Patient;
import com.hospitalcrudapp.dao.repositories.CredentialRepository;
import com.hospitalcrudapp.dao.repositories.JDBC.SQLQueries;
import com.hospitalcrudapp.dao.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

@Repository
@Profile("active")
public class SpringPatientRepository implements PatientRepository {
    @Autowired
    private JdbcClient jdbcClient;
    private final PatientRowMapper patientRowMapper;
    private final CredentialRepository credentialRepository;

    public SpringPatientRepository(PatientRowMapper patientRowMapper, CredentialRepository credentialRepository, SpringPaymentRepository paymentRepository) {
        this.patientRowMapper = patientRowMapper;
        this.credentialRepository = credentialRepository;
    }

    @Override
    public List<Patient> getAll() {
        return jdbcClient.sql(SQLQueries.GET_PATIENTS)
                .query(patientRowMapper)
                .list();
    }

    @Override
    @Transactional
    public int add(Patient patient) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(SQLQueries.ADD_PATIENT)
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
        jdbcClient.sql(SQLQueries.UPDATE_PATIENT)
                .param(1, patient.getName())
                .param(2, patient.getBirthDate())
                .param(3, patient.getPhoneNumber())
                .param(4, patient.getId())
                .update();
    }

    @Override
    @Transactional
    public int delete(int id, boolean confirm) {
        jdbcClient.sql(SQLQueries.DELETE_MEDS_PATIENTS)
                .param(1, id)
                .update();

        jdbcClient.sql(SQLQueries.DELETE_MEDREC_PATIENTS)
                .param(1, id)
                .update();

        jdbcClient.sql(SQLQueries.DELETE_CREDENTIALS_PATIENT)
                .param(1, id)
                .update();
        return jdbcClient.sql(SQLQueries.DELETE_PATIENT)
                .param(1, id)
                .update();
    }
}
