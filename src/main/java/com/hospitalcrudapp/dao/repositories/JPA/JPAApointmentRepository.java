package com.hospitalcrudapp.dao.repositories.JPA;

import com.hospitalcrudapp.dao.model.Appointment;
import com.hospitalcrudapp.dao.model.Payment;
import com.hospitalcrudapp.dao.repositories.AppointmentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Repository;

@Repository
public class JPAApointmentRepository implements AppointmentRepository {

    private EntityManager entityManager;
    private final JPAUtil jpaUtil;

    public JPAApointmentRepository(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public Appointment get(int patientId) {
        entityManager = jpaUtil.getEntityManager();
        Appointment appointment = null;
        try {
            appointment = entityManager
                    .createNamedQuery("HQL_GET_APPOINTMENTS", Appointment.class)
                    .setParameter(1, patientId)
                    .getSingleResult();


        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null)
                entityManager.close();
        }
        return appointment;
    }
}
