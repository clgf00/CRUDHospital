package com.hospitalcrudapp.dao.repositories.JDBC;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
@Profile("active")
public class JDBCPaymentRepository {

    private final DBConnectionPool dbConnection;


    public JDBCPaymentRepository(DBConnectionPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    public double getTotalPayments(int patientId) {
        double totalPayments = 0.0;
        try (Connection con = dbConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQLQueries.TOTAL_PAYMENTS_QUERY)) {

            pstmt.setInt(1, patientId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                totalPayments = rs.getDouble(1);
            }
        } catch (SQLException sqle) {
            Logger.getLogger(JDBCPatientRepository.class.getName()).log(Level.SEVERE, null, sqle);
        }
        return totalPayments;
    }
}
