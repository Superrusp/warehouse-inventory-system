package com.kpi.springlabs.backend.repository.jdbc.impl;

import com.kpi.springlabs.backend.enums.SqlOperationType;
import com.kpi.springlabs.backend.model.Goods;
import com.kpi.springlabs.backend.model.GoodsInShop;
import com.kpi.springlabs.backend.model.Shop;
import com.kpi.springlabs.backend.repository.jdbc.GoodsInShopRepository;
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
public class GoodsInShopRepositoryImpl extends BaseRepositoryImpl<GoodsInShop> implements GoodsInShopRepository {

    private final GoodsRepositoryImpl goodsRepositoryImpl;
    private final ShopRepository shopRepository;

    @Autowired
    public GoodsInShopRepositoryImpl(GoodsRepositoryImpl goodsRepositoryImpl, ShopRepository warehouseRepository) {
        this.goodsRepositoryImpl = goodsRepositoryImpl;
        this.shopRepository = warehouseRepository;
    }

    @Override
    protected String getTableName() {
        return Constants.TableNames.GOODS_IN_SHOPS;
    }

    @Override
    protected GoodsInShop getMappedEntity(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(1);
        long goodsId = resultSet.getLong(2);
        long shopId = resultSet.getLong(3);
        int availableQuantity = resultSet.getInt(4);

        Optional<Goods> goods = goodsRepositoryImpl.getById(goodsId);
        Optional<Shop> shop = shopRepository.getById(shopId);

        GoodsInShop goodsInShop = null;
        if (goods.isPresent() && shop.isPresent()) {
            LOG.debug("Goods: {}", goods);
            LOG.debug("Shop: {}", shop);
            goodsInShop = new GoodsInShop(id, shop.get(), goods.get(), availableQuantity);
        }
        return goodsInShop;
    }

    @Override
    protected PreparedStatement getPreparedStatementForSqlOperation(GoodsInShop goodsInShop, SqlOperationType sqlOperationType) throws SQLException {
        String query;
        if (SqlOperationType.INSERT == sqlOperationType) {
            query = "INSERT INTO goods_in_shops (goods_id, shop_id, available_quantity) VALUES (?, ?, ?)";
        } else {
            query = "UPDATE goods_in_shops SET goods_id = ?, shop_id = ?, available_quantity = ? WHERE id = ?";
        }

        Connection connection = jdbcTemplateUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, goodsInShop.getGoods().getId());
        statement.setLong(2, goodsInShop.getShop().getId());
        statement.setInt(3, goodsInShop.getAvailableQuantity());

        if (SqlOperationType.UPDATE == sqlOperationType) {
            statement.setLong(4, goodsInShop.getId());
        }
        return statement;
    }

    @Override
    public List<GoodsInShop> getAllGoodsByShopId(long shopId) {
        LOG.debug("Getting all goods by shopID: {}", shopId);
        List<GoodsInShop> allGoodsInShop = new ArrayList<>();
        String query = "SELECT * FROM goods_in_shops WHERE shop_id = ?";

        ResultSet resultSet = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = jdbcTemplateUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setLong(1, shopId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                GoodsInShop goodsInShop = getMappedEntity(resultSet);
                LOG.debug("GoodsInStock: {}", goodsInShop);
                allGoodsInShop.add(goodsInShop);
            }
            connection.commit();
        } catch (SQLException e) {
            LOG.error("Error occurred while querying for entity(id = {}) in {} ", shopId, this.getClass().getName());
            jdbcTemplateUtils.rollbackTransaction(connection);
        } finally {
            jdbcTemplateUtils.closeResources(resultSet, statement, connection);
        }
        return allGoodsInShop;
    }

    @Override
    public Optional<GoodsInShop> getGoodsByIdAndShop(long shopId, long goodsId) {
        LOG.debug("Getting goods by id {} and shopID {}", goodsId, shopId);
        String query = "SELECT * FROM goods_in_shops WHERE goods_id = ? AND shop_id = ?";
        GoodsInShop goodsInShop = null;

        ResultSet resultSet = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = jdbcTemplateUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setLong(1, goodsId);
            statement.setLong(2, shopId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                goodsInShop = getMappedEntity(resultSet);
                LOG.debug("GoodsInShop: {}", goodsInShop);
            }
            connection.commit();
        } catch (SQLException e) {
            LOG.error("Error occurred while querying for entity(id = {}) in {} ", shopId, this.getClass().getName());
            jdbcTemplateUtils.rollbackTransaction(connection);
        } finally {
            jdbcTemplateUtils.closeResources(resultSet, statement, connection);
        }

        if (goodsInShop != null) {
            return Optional.of(goodsInShop);
        }
        return Optional.empty();
    }
}
