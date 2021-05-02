package com.kpi.springlabs.backend.repository.jdbc.impl;

import com.kpi.springlabs.backend.enums.SqlOperationType;
import com.kpi.springlabs.backend.model.Goods;
import com.kpi.springlabs.backend.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@Slf4j
public class GoodsRepositoryImpl extends BaseRepositoryImpl<Goods> {

    @Override
    protected String getTableName() {
        return Constants.TableNames.GOODS;
    }

    @Override
    protected Goods getMappedEntity(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(1);
        String name = resultSet.getString(2);
        String description = resultSet.getString(3);
        double price = resultSet.getDouble(4);
        return new Goods(id, name, description, price);
    }

    @Override
    protected PreparedStatement getPreparedStatementForSqlOperation(Goods goods, SqlOperationType sqlOperationType) throws SQLException {
        String query;
        if (SqlOperationType.INSERT == sqlOperationType) {
            query = "INSERT INTO goods (name, description, price) VALUES (?, ?, ?)";
        } else {
            query = "UPDATE goods SET name = ?, description = ?, price = ? WHERE id = ?";
        }
        Connection connection = jdbcTemplateUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, goods.getName());
        statement.setString(2, goods.getDescription());
        statement.setDouble(3, goods.getPrice());

        if (SqlOperationType.UPDATE == sqlOperationType) {
            statement.setLong(4, goods.getId());
        }
        return statement;
    }
}
