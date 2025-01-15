package com.hospitalcrudapp.domain.services;

import com.hospitalcrudapp.dao.model.Credential;
import com.hospitalcrudapp.dao.repositories.JPA.JPACredentialRepository;
import com.hospitalcrudapp.domain.model.CredentialUi;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class CredentialService {
    private final JPACredentialRepository credentialRepository;

    public CredentialService(JPACredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }
    public boolean get(CredentialUi credentialui) {
        Credential credential= this.credentialRepository.get(credentialui.getUsername());
        boolean aux;
        aux = Objects.equals(credential.getPassword(), credentialui.getPassword());
        return aux;
    }

    public boolean login(Credential credential) {
        Credential storedCredential = credentialRepository.get(credential.getUsername());
        return storedCredential != null && Objects.equals(storedCredential.getPassword(), credential.getPassword());
    }
}
