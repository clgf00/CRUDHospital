package com.hospitalcrudapp.dao.repositories.JDBC;


import com.hospitalcrudapp.dao.model.Credential;
import com.hospitalcrudapp.dao.model.errors.DuplicatedUserError;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
@Profile("null")
public class JDBCCredentialRepository {
    private final DBConnectionPool dbConnection;

    public JDBCCredentialRepository(DBConnectionPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<Credential> getAll() {
        List<Credential> credentials = new ArrayList<>();
        String query = SQLQueries.GET_CREDENTIALS;

        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                Credential credential = Credential.builder()
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .patientId(rs.getInt("user_id"))
                        .build();
                credentials.add(credential);
            }
        } catch (DataIntegrityViolationException | SQLException e) {
            Logger.getLogger(JDBCCredentialRepository.class.getName()).log(Level.SEVERE, "Error fetching credentials", e);
            throw new DuplicatedUserError(e.getMessage());
        }

        return credentials;
    }
}
