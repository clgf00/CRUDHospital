package com.hospitalcrudapp.ui;
import com.hospitalcrudapp.dao.model.Credential;
import com.hospitalcrudapp.domain.services.CredentialService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
    public class RestCredential {

    private final CredentialService credentialService;

    public RestCredential(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/login")
    public boolean login(@RequestBody Credential userCredentials) {
        return credentialService.login(userCredentials);
    }
}