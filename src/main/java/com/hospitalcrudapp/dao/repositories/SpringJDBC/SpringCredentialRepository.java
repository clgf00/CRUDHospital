package com.hospitalcrudapp.dao.repositories.SpringJDBC;

import com.hospitalcrudapp.dao.mappers.CredentialRowMapper;
import com.hospitalcrudapp.dao.model.Credential;
import com.hospitalcrudapp.dao.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        return jdbcClient.sql("select * from user_login where username = ?")
                .param(1, username)
                .query(credentialRowMapper)
                .single();
    }

    @Override
    public List<Credential> getAll() {
        return List.of();
    }
}
