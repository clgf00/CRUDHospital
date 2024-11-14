package com.hospitalcrudapp.dao.repositories.staticDAO;
import com.hospitalcrudapp.dao.model.Credential;
import com.hospitalcrudapp.dao.repositories.CredentialRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
@Profile("null")
public class StaticCredentialRepository implements CredentialRepository {
    public List<Credential> getAll() {
        List<Credential> credentials = new ArrayList<>();
        credentials.add(new Credential("user", "user", 1));
        credentials.add(new Credential("root", "quevedo2dam", 2));
        credentials.add(new Credential("usuario1", "usuario1", 3));

        return credentials;
    }

    public Credential get(String username) {
        return new Credential("root", "root", 1);
    }

    @Override
    public void add(Credential credential) {

    }
}
