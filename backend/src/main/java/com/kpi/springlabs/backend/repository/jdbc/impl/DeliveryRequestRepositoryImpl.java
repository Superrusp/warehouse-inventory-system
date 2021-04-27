package com.kpi.springlabs.backend.repository.jdbc.impl;

import com.kpi.springlabs.backend.model.DeliveryRequest;
import com.kpi.springlabs.backend.model.Goods;
import com.kpi.springlabs.backend.model.Shop;
import com.kpi.springlabs.backend.model.Warehouse;
import com.kpi.springlabs.backend.repository.jdbc.DeliveryRequestRepository;
import com.kpi.springlabs.backend.utils.JdbcTemplateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class DeliveryRequestRepositoryImpl implements DeliveryRequestRepository {

    private final JdbcTemplateUtils jdbcTemplateUtils;
    private final GoodsRepositoryImpl goodsRepositoryImpl;
    private final WarehouseRepository warehouseRepository;
    private final ShopRepository shopRepository;

    @Autowired
    public DeliveryRequestRepositoryImpl(JdbcTemplateUtils jdbcTemplateUtils, GoodsRepositoryImpl goodsRepositoryImpl,
                                         WarehouseRepository warehouseRepository, ShopRepository shopRepository) {
        this.jdbcTemplateUtils = jdbcTemplateUtils;
        this.goodsRepositoryImpl = goodsRepositoryImpl;
        this.warehouseRepository = warehouseRepository;
        this.shopRepository = shopRepository;
    }

    @Override
    public List<DeliveryRequest> getAll() {
        LOG.debug("Getting all delivery requests");
        List<DeliveryRequest> deliveryRequests = new ArrayList<>();
        String query = "SELECT * FROM delivery_requests";

        ResultSet resultSet = null;
        Statement statement = null;
        Connection connection = null;
        try {
            connection = jdbcTemplateUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                long warehouseId = resultSet.getLong(2);
                long shopId = resultSet.getLong(3);
                LocalDate requestDate = resultSet.getDate(4).toLocalDate();
                LocalDate arrivalDate = resultSet.getDate(5).toLocalDate();

                List<Goods> goods = getGoodsOfDeliveryRequest(id);
                Optional<Shop> shop = shopRepository.getById(shopId);
                Optional<Warehouse> warehouse = warehouseRepository.getById(warehouseId);

                DeliveryRequest deliveryRequest = new DeliveryRequest(id, goods, requestDate, arrivalDate);
                shop.ifPresent(deliveryRequest::setShop);
                warehouse.ifPresent(deliveryRequest::setWarehouse);
                LOG.debug("Delivery Request: {}", deliveryRequest);
                deliveryRequests.add(deliveryRequest);
            }
            connection.commit();
        } catch (SQLException e) {
            LOG.error("Error occurred while querying for all delivery requests");
            jdbcTemplateUtils.rollbackTransaction(connection);
        } finally {
            jdbcTemplateUtils.closeResources(resultSet, statement, connection);
        }

        return deliveryRequests;
    }

    @Override
    public Optional<DeliveryRequest> getById(long deliveryRequestId) {
        LOG.debug("Getting delivery request by id: {}", deliveryRequestId);
        String query = "SELECT * FROM delivery_requests WHERE delivery_request_id = ?";

        ResultSet resultSet = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = jdbcTemplateUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setLong(1, deliveryRequestId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                long warehouseId = resultSet.getLong(2);
                long shopId = resultSet.getLong(3);
                LocalDate requestDate = resultSet.getDate(4).toLocalDate();
                LocalDate arrivalDate = resultSet.getDate(5).toLocalDate();

                List<Goods> goods = getGoodsOfDeliveryRequest(id);
                Optional<Shop> shop = shopRepository.getById(shopId);
                Optional<Warehouse> warehouse = warehouseRepository.getById(warehouseId);

                DeliveryRequest deliveryRequest = new DeliveryRequest(id, goods, requestDate, arrivalDate);
                shop.ifPresent(deliveryRequest::setShop);
                warehouse.ifPresent(deliveryRequest::setWarehouse);
                LOG.debug("Delivery Request: {}", deliveryRequest);
                return Optional.of(deliveryRequest);
            }
            connection.commit();
        } catch (SQLException e) {
            LOG.error("Error occurred while querying for DeliveryRequest(id = {})", deliveryRequestId);
            jdbcTemplateUtils.rollbackTransaction(connection);
        } finally {
            jdbcTemplateUtils.closeResources(resultSet, statement, connection);
        }
        return Optional.empty();
    }

    @Override
    public Optional<DeliveryRequest> save(DeliveryRequest deliveryRequest) {
        LOG.debug("Inserting delivery request {}", deliveryRequest);
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        Connection connection1 = null;
        Connection connection2 = null;
        try {
            LOG.debug("Inserting into delivery_requests");
            connection1 = jdbcTemplateUtils.getConnection();
            statement1 = connection1.prepareStatement("INSERT INTO delivery_requests (shop_id, warehouse_id, request_date, arrival_date)" +
                    " VALUES (?, ?, ?, ?)", new String[]{"delivery_request_id"});

            Warehouse warehouse = deliveryRequest.getWarehouse();
            if (warehouse != null) {
                statement1.setLong(1, warehouse.getId());
            } else {
                statement1.setNull(1, Types.INTEGER);
            }

            Shop shop = deliveryRequest.getShop();
            if (shop != null) {
                statement1.setLong(2, shop.getId());
            } else {
                statement1.setNull(2, Types.INTEGER);
            }

            statement1.setObject(3, deliveryRequest.getRequestDate());
            statement1.setObject(4, deliveryRequest.getArrivalDate());
            statement1.executeUpdate();
            connection1.commit();
            LOG.debug("Executed successfully");

            ResultSet generatedKeys = statement1.getGeneratedKeys();
            if (generatedKeys.next()) {
                LOG.debug("Inserting into delivery_request_goods");
                connection2 = jdbcTemplateUtils.getConnection();
                statement2 = connection2.prepareStatement("INSERT INTO delivery_request_goods (delivery_request_id, goods_id) VALUES (?, ?)");
                List<Goods> goodsDeliveryRequest = deliveryRequest.getGoods();
                for (Goods goods : goodsDeliveryRequest) {
                    statement2.setLong(1, generatedKeys.getLong(1));
                    statement2.setLong(2, goods.getId());
                    statement2.addBatch();
                }
                statement2.executeBatch();
                connection2.commit();
                LOG.debug("Executed successfully");
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while inserting delivery request: {}", deliveryRequest);
            jdbcTemplateUtils.rollbackTransaction(connection1);
            jdbcTemplateUtils.rollbackTransaction(connection2);
        } finally {
            jdbcTemplateUtils.closeResources(null, statement1, connection1);
            jdbcTemplateUtils.closeResources(null, statement2, connection2);
        }
        return Optional.of(deliveryRequest);
    }

    @Override
    public void update(DeliveryRequest deliveryRequest) {
        LOG.debug("Updating delivery request {}", deliveryRequest);
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        Connection connection1 = null;
        Connection connection2 = null;
        try {
            LOG.debug("Updating delivery_requests");
            String query = "UPDATE delivery_requests SET warehouse_id = ?, shop_id = ?, request_date = ?, arrival_date = ? " +
                    "WHERE delivery_request_id = ?";
            connection1 = jdbcTemplateUtils.getConnection();
            statement1 = connection1.prepareStatement(query);

            Warehouse warehouse = deliveryRequest.getWarehouse();
            if (warehouse != null) {
                statement1.setLong(1, warehouse.getId());
            } else {
                statement1.setNull(1, Types.INTEGER);
            }

            Shop shop = deliveryRequest.getShop();
            if (shop != null) {
                statement1.setLong(2, shop.getId());
            } else {
                statement1.setNull(2, Types.INTEGER);
            }

            statement1.setObject(3, deliveryRequest.getRequestDate());
            statement1.setObject(4, deliveryRequest.getArrivalDate());
            statement1.setLong(5, deliveryRequest.getId());

            statement1.executeUpdate();
            connection1.commit();
            LOG.debug("Executed successfully");

            LOG.debug("Updating delivery_request_goods");
            connection2 = jdbcTemplateUtils.getConnection();
            statement2 = connection2.prepareStatement("INSERT INTO delivery_request_goods (delivery_request_id, goods_id) VALUES (?, ?)");
            List<Goods> goodsDeliveryRequest = deliveryRequest.getGoods();
            for (Goods goods : goodsDeliveryRequest) {
                if (!existsGoodsInDeliveryRequest(deliveryRequest.getId(), goods.getId())) {
                    statement2.setLong(1, deliveryRequest.getId());
                    statement2.setLong(2, goods.getId());
                    statement2.addBatch();
                }
            }
            statement2.executeBatch();
            connection2.commit();
            LOG.debug("Executed successfully");
        } catch (SQLException e) {
            LOG.error("Error occurred while updating delivery request: {}", deliveryRequest);
            jdbcTemplateUtils.rollbackTransaction(connection1);
            jdbcTemplateUtils.rollbackTransaction(connection2);
        } finally {
            jdbcTemplateUtils.closeResources(null, statement1, connection1);
            jdbcTemplateUtils.closeResources(null, statement2, connection2);
        }
    }

    @Override
    public void delete(long id) {
        LOG.debug("Deleting delivery request by id {}", id);
        Connection connection = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        try {
            connection = jdbcTemplateUtils.getConnection();
            statement1 = connection.prepareStatement("DELETE FROM delivery_request_goods WHERE delivery_request_id = ?");
            statement1.setLong(1, id);
            statement1.executeUpdate();
            connection.commit();

            statement2 = connection.prepareStatement("DELETE FROM delivery_requests WHERE delivery_request_id = ?");
            statement2.setLong(1, id);
            statement2.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LOG.error("Error occurred while deleting delivery request id: {}", id);
            jdbcTemplateUtils.rollbackTransaction(connection);
        } finally {
            jdbcTemplateUtils.closeResources(null, statement1, connection);
        }
    }

    @Override
    public boolean exists(long id) {
        LOG.debug("Checking if DeliveryRequest(id = {}) exists", id);
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM delivery_requests WHERE delivery_request_id = ?";
            connection = jdbcTemplateUtils.getConnection();
            statement = connection.prepareStatement(query);

            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                LOG.debug("DeliveryRequest exists");
                return true;
            }
            connection.commit();
        } catch (SQLException e) {
            LOG.error("Error occurred while searching for delivery request");
            jdbcTemplateUtils.rollbackTransaction(connection);
        } finally {
            jdbcTemplateUtils.closeResources(resultSet, statement, connection);
        }
        LOG.debug("DeliveryRequest does not exist");
        return false;

    }

    protected List<Goods> getGoodsOfDeliveryRequest(long deliveryRequestId) {
        LOG.debug("Getting all goods in delivery request(id = {})", deliveryRequestId);
        List<Goods> allGoodsDeliveryRequest = new ArrayList<>();
        String query = "SELECT goods_id FROM delivery_request_goods WHERE delivery_request_id = ?";

        ResultSet resultSet = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = jdbcTemplateUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setLong(1, deliveryRequestId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long goodsId = resultSet.getLong(1);
                Optional<Goods> goods = goodsRepositoryImpl.getById(goodsId);
                LOG.debug("Goods: {}", goods);
                goods.ifPresent(allGoodsDeliveryRequest::add);
            }
            connection.commit();
        } catch (SQLException e) {
            LOG.error("Error occurred while querying for all goods in delivery request");
            jdbcTemplateUtils.rollbackTransaction(connection);
        } finally {
            jdbcTemplateUtils.closeResources(resultSet, statement, connection);
        }

        return allGoodsDeliveryRequest;
    }

    @Override
    public List<DeliveryRequest> getDeliveryRequestsByGoodsId(long goodsId) {
        LOG.debug("Getting delivery requests by Goods(id = {})", goodsId);
        List<DeliveryRequest> deliveryRequests = new ArrayList<>();
        String query = "SELECT delivery_request_id FROM delivery_request_goods WHERE goods_id = ?";

        ResultSet resultSet = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = jdbcTemplateUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setLong(1, goodsId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long delivertRequestId = resultSet.getLong(1);
                Optional<DeliveryRequest> deliveryRequest = getById(delivertRequestId);
                LOG.debug("Delivery Request: {}", deliveryRequest);
                deliveryRequest.ifPresent(deliveryRequests::add);
            }
            connection.commit();
        } catch (SQLException e) {
            LOG.error("Error occurred while querying for all delivery requests by Goods(id = {})", goodsId);
            jdbcTemplateUtils.rollbackTransaction(connection);
        } finally {
            jdbcTemplateUtils.closeResources(resultSet, statement, connection);
        }

        return deliveryRequests;
    }

    @Override
    public boolean existsGoodsInDeliveryRequest(long deliveryRequestId, long goodsId) {
        LOG.debug("Checking if goods(id = {}) exists in delivery request(id = {})", goodsId, deliveryRequestId);
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT goods_id FROM delivery_request_goods WHERE delivery_request_id = ? AND goods_id = ?";
            connection = jdbcTemplateUtils.getConnection();
            statement = connection.prepareStatement(query);

            statement.setLong(1, deliveryRequestId);
            statement.setLong(2, goodsId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                LOG.debug("Goods exists in delivery request");
                return true;
            }
            connection.commit();
        } catch (SQLException e) {
            LOG.error("Error occurred while searching for goods in delivery request");
            jdbcTemplateUtils.rollbackTransaction(connection);
        } finally {
            jdbcTemplateUtils.closeResources(resultSet, statement, connection);
        }
        LOG.debug("Goods does not exist in delivery request");
        return false;
    }
}
