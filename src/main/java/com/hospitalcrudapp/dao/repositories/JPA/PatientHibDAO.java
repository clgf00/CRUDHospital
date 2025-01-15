package com.hospitalcrudapp.dao.repositories.JPA;

import com.hospitalcrudapp.dao.model.Patient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PatientHibDAO {
    private JPAUtil jpaUtil;

    public List<Patient> getAll() {
        List<Patient> list = null;
        EntityManager em = null;

        try {
            em = jpaUtil.getEntityManager();
            list = em.createQuery("FROM Patient", Patient.class).getResultList();
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
        return list;
    }

    public Patient get(int id) {
        Patient patient = null;
        EntityManager em = null;

        try {
            em = jpaUtil.getEntityManager();
            patient = em.find(Patient.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }

        return patient;
    }

    public void add(Patient patient) {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = jpaUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.persist(patient);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }

    public void delete(Patient patient) {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = jpaUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.remove(em.merge(patient));
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }

    public void update(Patient patient) {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = jpaUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.merge(patient);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }
}
