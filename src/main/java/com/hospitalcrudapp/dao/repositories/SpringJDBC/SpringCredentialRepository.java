package com.hospitalcrudapp.dao.repositories.SpringJDBC;

import com.hospitalcrudapp.dao.mappers.CredentialRowMapper;
import com.hospitalcrudapp.dao.model.Credential;
import com.hospitalcrudapp.dao.repositories.CredentialRepository;
import com.hospitalcrudapp.dao.repositories.JDBC.SQLQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

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
    public Credential get(String username){
              return jdbcClient.sql(SQLQueries.GET_CREDENTIALS)
                .param(1, username)
                .query(credentialRowMapper)
                .single();
    }

    @Override
    public void add(Credential credential) {
        jdbcClient.sql(SQLQueries.ADD_CREDENTIALS)
                .param(1, credential.getUsername())
                .param(2, credential.getPassword())
                .update();
    }
}
