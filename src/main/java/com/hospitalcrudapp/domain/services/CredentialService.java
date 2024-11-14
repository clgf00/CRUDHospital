package com.hospitalcrudapp.domain.services;
import com.hospitalcrudapp.dao.model.Credential;
import com.hospitalcrudapp.dao.repositories.CredentialRepository;
import com.hospitalcrudapp.dao.repositories.SpringJDBC.SpringCredentialRepository;
import com.hospitalcrudapp.domain.model.CredentialUi;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
@Service
public class CredentialService {
    private final SpringCredentialRepository credentialRepository;

    public CredentialService(SpringCredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }
    public boolean get(CredentialUi credentialui) {

        Credential credentialRepository= this.credentialRepository.get(credentialui.getUsername());

        boolean aux;
        if (Objects.equals(credentialRepository.getPassword(), credentialui.getPassword())) {
            aux = true;
        } else aux = false;
        return aux;
    }


    public boolean login(Credential credential) {
        Credential storedCredential = credentialRepository.get(credential.getUsername());
        if (storedCredential != null && Objects.equals(storedCredential.getPassword(), credential.getPassword())) {
            return true;
        }
        return false;
    }
}
