package com.hospitalcrudapp.dao.repositories.JPA;

import com.hospitalcrudapp.dao.model.Payment;
import com.hospitalcrudapp.dao.repositories.PaymentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Repository;

@Repository
public class JPAPaymentRepository implements PaymentRepository {

    private EntityManager entityManager;
    private final JPAUtil jpaUtil;

    public JPAPaymentRepository(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public double get(int patientId) {
        entityManager = jpaUtil.getEntityManager();
        float payment = 0;
        try {
            Payment payment1 = entityManager
                    .createNamedQuery("HQL_GET_PAYMENT", Payment.class)
                    .setParameter(1, patientId)
                    .getSingleResult();
            payment = payment1.getAmount();

        } catch(NoResultException e) {
            return 0;
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null)
                entityManager.close();
        }
        return payment;
    }
}
