package com.kpi.springlabs.backend.repository.jdbc.impl;

import com.kpi.springlabs.backend.enums.SqlOperationType;
import com.kpi.springlabs.backend.model.DeliveryItem;
import com.kpi.springlabs.backend.model.DeliveryRequest;
import com.kpi.springlabs.backend.model.Goods;
import com.kpi.springlabs.backend.repository.jdbc.DeliveryRequestRepository;
import com.kpi.springlabs.backend.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@Slf4j
public class DeliveryItemRepository extends BaseRepositoryImpl<DeliveryItem> {

    private final GoodsRepositoryImpl goodsRepositoryImpl;
    private final DeliveryRequestRepository deliveryRequestRepository;

    @Autowired
    public DeliveryItemRepository(GoodsRepositoryImpl goodsRepositoryImpl, DeliveryRequestRepository deliveryRequestRepository) {
        this.goodsRepositoryImpl = goodsRepositoryImpl;
        this.deliveryRequestRepository = deliveryRequestRepository;
    }

    @Override
    protected String getTableName() {
        return Constants.TableNames.DELIVERY_ITEMS;
    }

    @Override
    protected DeliveryItem getMappedEntity(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(1);
        long goodsId = resultSet.getLong(2);
        long deliveryRequestId = resultSet.getLong(3);
        String deliveryStatus = resultSet.getString(4);

        Optional<Goods> goods = goodsRepositoryImpl.getById(goodsId);
        Optional<DeliveryRequest> deliveryRequest = deliveryRequestRepository.getById(deliveryRequestId);

        DeliveryItem deliveryItem = null;
        if (goods.isPresent() && deliveryRequest.isPresent()) {
            LOG.debug("Goods: {}", goods);
            LOG.debug("Delivery request: {}", deliveryRequest);
            deliveryItem = new DeliveryItem(id, goods.get(), deliveryRequest.get(), deliveryStatus);
        }
        return deliveryItem;
    }

    @Override
    protected PreparedStatement getPreparedStatementForSqlOperation(DeliveryItem deliveryItem, SqlOperationType sqlOperationType) throws SQLException {
        String query;
        if (SqlOperationType.INSERT == sqlOperationType) {
            query = "INSERT INTO delivery_items (goods_id, delivery_request_id, delivery_status) VALUES (?, ?, ?)";
        } else {
            query = "UPDATE delivery_items SET goods_id = ?, delivery_request_id = ?, delivery_status = ? WHERE id = ?";
        }

        Connection connection = jdbcTemplateUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, deliveryItem.getGoods().getId());
        statement.setLong(2, deliveryItem.getDeliveryRequest().getId());
        statement.setString(3, deliveryItem.getDeliveryStatus());

        if (SqlOperationType.UPDATE == sqlOperationType) {
            statement.setLong(4, deliveryItem.getId());
        }
        LOG.debug("Statement: {}", statement.toString());
        return statement;
    }
}
