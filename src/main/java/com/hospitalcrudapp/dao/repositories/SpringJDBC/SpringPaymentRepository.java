package com.hospitalcrudapp.dao.repositories.SpringJDBC;

import com.hospitalcrudapp.dao.repositories.JDBC.DBConnectionPool;
import com.hospitalcrudapp.dao.repositories.JDBC.SQLQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
@Profile("active")
public class SpringPaymentRepository {
    @Autowired
    private JdbcClient jdbcClient;

    public double getTotalPayments(int patientId) {
        String sql = SQLQueries.SUM_PAYMENTS;
        try {
            List<Double> result = jdbcClient
                    .sql(sql)
                    .param(1, patientId)
                    .query(Double.class)
                    .list();

            return result.stream()
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(0.0);

        } catch (Exception e) {
            Logger.getLogger(SpringPaymentRepository.class.getName()).log(Level.SEVERE, "Error al obtener los pagos", e);
            return 0.0;
        }
    }
}
