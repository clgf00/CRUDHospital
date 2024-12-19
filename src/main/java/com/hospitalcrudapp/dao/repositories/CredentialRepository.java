package com.hospitalcrudapp.dao.repositories;

import com.hospitalcrudapp.dao.model.Credential;
import org.springframework.stereotype.Component;

@Component
public interface CredentialRepository {
    Credential get(String username);
    void add(Credential credential);

}