package com.hospitalcrudapp.dao.repositories.JPA;

import com.hospitalcrudapp.dao.model.Doctor;
import com.hospitalcrudapp.dao.repositories.DoctorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JPADoctorRepository implements DoctorRepository {
    private EntityManager entityManager;
    private final JPAUtil jpaUtil;

    public JPADoctorRepository(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    @Transactional
    public List<Doctor> getAll() {
        List list = List.of();
        entityManager = jpaUtil.getEntityManager();
        try {
            list = entityManager
                    .createNamedQuery("HQL_GET_ALL_DOCTORS", Doctor.class)
                    .getResultList();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
        return list;
    }
}

