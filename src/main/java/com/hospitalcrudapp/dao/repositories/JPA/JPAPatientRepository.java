package com.hospitalcrudapp.dao.repositories.JPA;

import com.hospitalcrudapp.dao.model.Patient;
import com.hospitalcrudapp.dao.repositories.PatientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JPAPatientRepository implements PatientRepository {

    private EntityManager entityManager;
    private final JPAUtil jpaUtil;

    public JPAPatientRepository(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public List<Patient> getAll() {
        List list = List.of();
        entityManager = jpaUtil.getEntityManager();

        try {
            list = entityManager
                    .createNamedQuery("HQL_GET_ALL_PATIENTS", Patient.class)
                    .getResultList();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }

        return list;
    }

    @Override
    public int add(Patient patient) {
        entityManager = jpaUtil.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        try {
            tx = entityManager.getTransaction();
            tx.begin();
            entityManager.persist(patient);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            if (entityManager != null)
                entityManager.close();
        }

        return 0;
    }

    @Override
    public void update(Patient patient) {
        entityManager = jpaUtil.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        try {
            if (patient != null) {
                entityManager.merge(patient);
                tx.commit();
            }
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            if (entityManager != null)
                entityManager.close();
        }
    }

    @Override
    public void delete(int id, boolean confirm) {
        entityManager = jpaUtil.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        try {
            Patient patient = new Patient(id, null, null, null, null);
            if (patient != null) {
                entityManager.remove(entityManager.merge(patient));
                tx.commit();
            }
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }
}
