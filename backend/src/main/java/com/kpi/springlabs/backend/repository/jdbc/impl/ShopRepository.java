package com.kpi.springlabs.backend.repository.jdbc.impl;

import com.kpi.springlabs.backend.enums.SqlOperationType;
import com.kpi.springlabs.backend.model.Shop;
import com.kpi.springlabs.backend.utils.Constants;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ShopRepository extends BaseRepositoryImpl<Shop> {

    @Override
    protected String getTableName() {
        return Constants.TableNames.SHOPS;
    }

    @Override
    protected Shop getMappedEntity(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(1);
        String name = resultSet.getString(2);
        return new Shop(id, name);
    }

    @Override
    protected PreparedStatement getPreparedStatementForSqlOperation(Shop shop, SqlOperationType sqlOperationType) throws SQLException {
        String query;
        if (SqlOperationType.INSERT == sqlOperationType) {
            query = "INSERT INTO shops (name) VALUES (?)";
        } else {
            query = "UPDATE shops SET name = ? WHERE id = ?";
        }

        Connection connection = jdbcTemplateUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, shop.getName());

        if (SqlOperationType.UPDATE == sqlOperationType) {
            statement.setLong(2, shop.getId());
        }
        return statement;
    }
}
