package com.kpi.springlabs.backend.repository.jdbc.impl;

import com.kpi.springlabs.backend.enums.SqlOperationType;
import com.kpi.springlabs.backend.model.Goods;
import com.kpi.springlabs.backend.model.GoodsInStock;
import com.kpi.springlabs.backend.model.Warehouse;
import com.kpi.springlabs.backend.repository.jdbc.GoodsInStockRepository;
import com.kpi.springlabs.backend.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class GoodsInStockRepositoryImpl extends BaseRepositoryImpl<GoodsInStock> implements GoodsInStockRepository {

    private final GoodsRepositoryImpl goodsRepositoryImpl;
    private final WarehouseRepository warehouseRepository;

    @Autowired
    public GoodsInStockRepositoryImpl(GoodsRepositoryImpl goodsRepositoryImpl, WarehouseRepository warehouseRepository) {
        this.goodsRepositoryImpl = goodsRepositoryImpl;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    protected String getTableName() {
        return Constants.TableNames.GOODS_IN_STOCKS;
    }

    @Override
    protected String getPrimaryKeyName() {
        return Constants.PrimaryKeys.GOODS_IN_STOCK_ID;
    }

    @Override
    protected GoodsInStock getMappedEntity(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(1);
        long goodsId = resultSet.getLong(2);
        long warehouseId = resultSet.getLong(3);
        int availableQuantity = resultSet.getInt(4);

        Optional<Goods> goods = goodsRepositoryImpl.getById(goodsId);
        Optional<Warehouse> warehouse = warehouseRepository.getById(warehouseId);

        GoodsInStock goodsInStock = null;
        if (goods.isPresent() && warehouse.isPresent()) {
            LOG.debug("Goods: {}", goods);
            LOG.debug("Warehouse: {}", warehouse);
            goodsInStock = new GoodsInStock(id, goods.get(), warehouse.get(), availableQuantity);
        }
        return goodsInStock;
    }

    @Override
    protected PreparedStatement getPreparedStatementForSqlOperation(GoodsInStock goodsInStock, SqlOperationType sqlOperationType) throws SQLException {
        String query;
        if (SqlOperationType.INSERT == sqlOperationType) {
            query = "INSERT INTO goods_in_stocks (goods_id, warehouse_id, available_quantity) VALUES (?, ?, ?)";
        } else {
            query = "UPDATE goods_in_stocks SET goods_id = ?, warehouse_id = ?, available_quantity = ? WHERE goods_in_stock_id = ?";
        }

        Connection connection = jdbcTemplateUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, goodsInStock.getGoods().getId());
        statement.setLong(2, goodsInStock.getWarehouse().getId());
        statement.setInt(3, goodsInStock.getAvailableQuantity());

        if (SqlOperationType.UPDATE == sqlOperationType) {
            statement.setLong(4, goodsInStock.getId());
        }
        return statement;
    }

    @Override
    public List<GoodsInStock> getAllGoodsByStockId(long stockId) {
        LOG.debug("Getting all goods by stockID: {}", stockId);
        List<GoodsInStock> allGoodsInStock = new ArrayList<>();
        String query = "SELECT * FROM goods_in_stocks WHERE warehouse_id = ?";

        ResultSet resultSet = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = jdbcTemplateUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setLong(1, stockId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                GoodsInStock goodsInStock = getMappedEntity(resultSet);
                LOG.debug("GoodsInStock: {}", goodsInStock);
                allGoodsInStock.add(goodsInStock);
            }
            connection.commit();
        } catch (SQLException e) {
            LOG.error("Error occurred while querying for entity(id = {}) in {} ", stockId, this.getClass().getName());
            jdbcTemplateUtils.rollbackTransaction(connection);
        } finally {
            jdbcTemplateUtils.closeResources(resultSet, statement, connection);
        }
        return allGoodsInStock;
    }

    @Override
    public Optional<GoodsInStock> getGoodsByIdAndStock(long stockId, long goodsId) {
        LOG.debug("Getting goods by id {} and stockID {}", goodsId, stockId);
        String query = "SELECT * FROM goods_in_stocks WHERE goods_id = ? AND warehouse_id = ?";
        GoodsInStock goodsInStock = null;

        ResultSet resultSet = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = jdbcTemplateUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setLong(1, goodsId);
            statement.setLong(2, stockId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                goodsInStock = getMappedEntity(resultSet);
                LOG.debug("GoodsInStock: {}", goodsInStock);
            }
            connection.commit();
        } catch (SQLException e) {
            LOG.error("Error occurred while querying for entity(id = {}) in {} ", stockId, this.getClass().getName());
            jdbcTemplateUtils.rollbackTransaction(connection);
        } finally {
            jdbcTemplateUtils.closeResources(resultSet, statement, connection);
        }

        if (goodsInStock != null) {
            return Optional.of(goodsInStock);
        }
        return Optional.empty();
    }
}
