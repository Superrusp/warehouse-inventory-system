package com.kpi.springlabs.backend.repository.jdbc.impl;

import com.kpi.springlabs.backend.enums.SqlOperationType;
import com.kpi.springlabs.backend.model.Warehouse;
import com.kpi.springlabs.backend.utils.Constants;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class WarehouseRepository extends BaseRepositoryImpl<Warehouse> {

    @Override
    protected String getTableName() {
        return Constants.TableNames.WAREHOUSES;
    }

    @Override
    protected String getPrimaryKeyName() {
        return Constants.PrimaryKeys.WAREHOUSE_ID;
    }

    @Override
    protected Warehouse getMappedEntity(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(1);
        String name = resultSet.getString(2);
        return new Warehouse(id, name);
    }

    @Override
    protected PreparedStatement getPreparedStatementForSqlOperation(Warehouse warehouse, SqlOperationType sqlOperationType) throws SQLException {
        String query;
        if (SqlOperationType.INSERT == sqlOperationType) {
            query = "INSERT INTO warehouses (name) VALUES (?)";
        } else {
            query = "UPDATE warehouses SET name = ? WHERE warehouse_id = ?";
        }

        Connection connection = jdbcTemplateUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, warehouse.getName());

        if (SqlOperationType.UPDATE == sqlOperationType) {
            statement.setLong(2, warehouse.getId());
        }
        return statement;
    }
}
