package com.hospitalcrudapp.dao.repositories;
import com.hospitalcrudapp.dao.model.Credential;
import java.util.List;

public interface CredentialRepository {
    Credential get(String username);
    List<Credential> getAll();
}