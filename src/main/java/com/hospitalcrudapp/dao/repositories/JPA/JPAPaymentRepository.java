package com.hospitalcrudapp.dao.repositories.JPA;

import com.hospitalcrudapp.dao.repositories.PaymentRepository;
import jakarta.persistence.EntityManager;

public class JPAPaymentRepository implements PaymentRepository {

    private EntityManager entityManager;
    private final JPAUtil jpaUtil;

    public JPAPaymentRepository(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public double getTotalPayments(int patientId) {
        return 0;
    }
}
