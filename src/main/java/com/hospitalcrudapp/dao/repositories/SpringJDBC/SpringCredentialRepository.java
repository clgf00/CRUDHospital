package com.hospitalcrudapp.dao.repositories.SpringJDBC;

import com.hospitalcrudapp.dao.mappers.CredentialRowMapper;
import com.hospitalcrudapp.dao.model.Credential;
import com.hospitalcrudapp.dao.model.errors.DuplicatedUserError;
import com.hospitalcrudapp.dao.repositories.CredentialRepository;
import com.hospitalcrudapp.dao.repositories.JDBC.JDBCCredentialRepository;
import com.hospitalcrudapp.dao.repositories.JDBC.SQLQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
@Profile("active")
public class SpringCredentialRepository implements CredentialRepository {
    @Autowired
    private JdbcClient jdbcClient;
    private final CredentialRowMapper credentialRowMapper;

    public SpringCredentialRepository(CredentialRowMapper credentialRowMapper) {
        this.credentialRowMapper = credentialRowMapper;
    }


    @Override
    public Credential get(String username) {
        return jdbcClient.sql(SQLQueries.GET_CREDENTIALS)
                .param(1, username)
                .query(credentialRowMapper)
                .single();
    }

    @Override
    public void add(Credential credential) {
        try {
            jdbcClient.sql(SQLQueries.ADD_CREDENTIALS)
                    .param(1, credential.getUsername())
                    .param(2, credential.getPassword())
                    .update();
        } catch (DuplicateKeyException e) {
            Logger.getLogger(JDBCCredentialRepository.class.getName()).log(Level.SEVERE, "Error inserting credentials", e);
            throw new DuplicatedUserError("The username '" + credential.getUsername() + "' is already taken.");
        }
    }
}
