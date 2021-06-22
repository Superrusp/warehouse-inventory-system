package com.kpi.springlabs.backend.utils;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
@PropertySource("classpath:db/oracle_db.properties")
@ConfigurationProperties(prefix = "db")
@Slf4j
@Setter
public class JdbcTemplateUtils {

    private String url;
    private String username;
    private String password;

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        connection.setAutoCommit(false);
        return connection;
    }

    public void rollbackTransaction(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOG.error("Error while rollback transaction");
                throw new RuntimeException();
            }
        }
    }

    public void closeResources(ResultSet resultSet, Statement statement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOG.error("Error while closing resultSet");
                throw new RuntimeException();
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOG.error("Error while closing statement");
                throw new RuntimeException();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.error("Error while closing connection");
                throw new RuntimeException();
            }
        }
    }
}
