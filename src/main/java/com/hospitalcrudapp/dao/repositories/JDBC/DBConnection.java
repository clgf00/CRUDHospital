package com.hospitalcrudapp.dao.repositories.JDBC;
import com.hospitalcrudapp.configuration.Configuration;
import org.springframework.stereotype.Component;

import java.sql.*;
@Component
public class DBConnection {

    //TODO UPDATE IT TO OUR CONFIGURATION XML
    //TODO GET NEW DATA FROM XML

    /**
     * @description Class for creating a connection to a DB using DriverManager class.
     * Reads the information from a property file (mysql-properties.xml)
     */

    private Configuration config;

    /**
     * Opens Database connection
     */
    public DBConnection(Configuration config) {
        this.config = config;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager
                .getConnection(
                        config.getProperty("urlDB"),
                        config.getProperty("user_name"),
                        config.getProperty("password"));
    }
}
