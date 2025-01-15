package com.hospitalcrudapp.dao.repositories.JPA;

import com.hospitalcrudapp.dao.model.Doctor;
import com.hospitalcrudapp.dao.model.MedRecord;
import com.hospitalcrudapp.dao.model.Patient;
import com.hospitalcrudapp.dao.repositories.MedRecordRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JPAMedRecordRepository implements MedRecordRepository {

    private EntityManager entityManager;
    private final JPAUtil jpaUtil;

    public JPAMedRecordRepository(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public List<MedRecord> getAll(int idPatient) {
        EntityManager entityManager = null;
        List<MedRecord> list = List.of();

        try {
            entityManager = jpaUtil.getEntityManager();
            list = entityManager
                    .createNamedQuery("HQL_GET_ALL_MEDRECORDS", MedRecord.class)
                    .setParameter("patient_id", idPatient)
                    .getResultList();
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }

        return list;
    }

    @Override
    public int add(MedRecord medRecord) {
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = jpaUtil.getEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            //se crea una instancia de paciente que será el
            // mismo del que se ha obtenido dicho record
            Patient patient = medRecord.getPatient();
            //y aquí se le asigna al paciente
            medRecord.setPatient(patient);
            entityManager.persist(medRecord);
            tx.commit();

            return medRecord.getId();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return -1;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    @Override
    public int update(MedRecord medRecord) {
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = jpaUtil.getEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();

            MedRecord updated = entityManager.merge(medRecord);
            tx.commit();

            return updated.getId();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return -1;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    @Override
    public void delete(int id) {
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = jpaUtil.getEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();

            MedRecord medRecord = entityManager.find(MedRecord.class, id);
            if (medRecord != null) {
                entityManager.remove(medRecord);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
    public Patient getPatientById(int patientId) {
        EntityManager entityManager = jpaUtil.getEntityManager();
        try {
            return entityManager.find(Patient.class, patientId);
        } catch (PersistenceException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
    public Doctor getDoctorById(int doctorId) {
        EntityManager entityManager = jpaUtil.getEntityManager();
        try {
            return entityManager.find(Doctor.class, doctorId);
        } catch (PersistenceException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
}
