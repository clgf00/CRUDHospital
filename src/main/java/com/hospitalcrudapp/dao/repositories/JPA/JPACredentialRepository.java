package com.hospitalcrudapp.dao.repositories.JPA;


import com.hospitalcrudapp.dao.model.Credential;
import com.hospitalcrudapp.dao.repositories.CredentialRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Repository;

@Repository
public class JPACredentialRepository implements CredentialRepository {
    private EntityManager entityManager;
    private final JPAUtil jpaUtil;

    public JPACredentialRepository(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public Credential get(String username) {
        entityManager = jpaUtil.getEntityManager();
        Credential credential = null;
        try {
            credential = entityManager
                    .createNamedQuery("HQL_GET_CREDENTIAL", Credential.class)
                    .setParameter(1, username)
                    .getSingleResult();
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null)
                entityManager.close();
        }
        return credential;
    }

    @Override
    public void add(Credential credential) { }
}
