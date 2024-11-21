package com.hospitalcrudapp.dao.repositories.SpringJDBC;

import com.hospitalcrudapp.dao.mappers.PatientRowMapper;
import com.hospitalcrudapp.dao.model.Patient;
import com.hospitalcrudapp.dao.model.errors.DuplicatedUserError;
import com.hospitalcrudapp.dao.model.errors.ForeignKeyConstraintError;
import com.hospitalcrudapp.dao.repositories.CredentialRepository;
import com.hospitalcrudapp.dao.repositories.JDBC.JDBCCredentialRepository;
import com.hospitalcrudapp.dao.repositories.JDBC.JDBCPatientRepository;
import com.hospitalcrudapp.dao.repositories.JDBC.SQLQueries;
import com.hospitalcrudapp.dao.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
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
        } catch (DuplicateKeyException e) {
            Logger.getLogger(JDBCCredentialRepository.class.getName()).log(Level.SEVERE, "Error inserting credentials", e);
            throw new DuplicatedUserError("The username '" + patient.getCredentials().getUsername() + "' is already taken.");
        }

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
        int rowsAffected = 0;
        if(!confirm){
            try{
                jdbcClient.sql(SQLQueries.DELETE_PATIENT)
                        .param(1, id)
                        .update();

            } catch(DataIntegrityViolationException e){
                Logger.getLogger(JDBCPatientRepository.class.getName()).log(Level.SEVERE, null, e);
                throw new ForeignKeyConstraintError(e.getMessage());
            }
        } else{
            try{
                jdbcClient.sql(SQLQueries.DELETE_CREDENTIALS_PATIENT)
                        .param(1, id)
                        .update();
                jdbcClient.sql(SQLQueries.DELETE_PAYMENTS)
                        .param(1, id)
                        .update();
                jdbcClient.sql(SQLQueries.DELETE_APPOINTMENTS)
                        .param(1, id)
                        .update();
                jdbcClient.sql(SQLQueries.DELETE_MEDS)
                        .param(1, id)
                        .update();
                jdbcClient.sql(SQLQueries.DELETE_MEDR)
                        .param(1, id)
                        .update();
                jdbcClient.sql(SQLQueries.DELETE_PATIENT)
                        .param(1, id)
                        .update();

            }catch (DataIntegrityViolationException e) {
                Logger.getLogger(JDBCPatientRepository.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return 0;
    }
}
