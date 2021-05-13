package com.kpi.springlabs.backend.repository.jpa.impl;

import com.kpi.springlabs.backend.model.GoodsInStock;
import com.kpi.springlabs.backend.repository.jpa.GoodsInStockJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class GoodsInStockJpaRepositoryImpl implements GoodsInStockJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GoodsInStock> getAllGoodsByStockId(long stockId) {
        LOG.debug("Call query for all goods in Warehouse(id = {})", stockId);
        TypedQuery<GoodsInStock> query = entityManager
                .createQuery("SELECT g FROM goods_in_stocks g WHERE g.warehouse.id=:stockId", GoodsInStock.class);
        return query
                .setParameter("stockId", stockId)
                .getResultList();
    }

    @Override
    public Optional<GoodsInStock> getGoodsByIdAndStock(long stockId, long goodsId) {
        LOG.debug("Call query for Goods(id = {}) in Warehouse(id = {})", goodsId, stockId);
        TypedQuery<GoodsInStock> query = entityManager
                .createQuery("SELECT g FROM goods_in_stocks g WHERE g.warehouse.id=:stockId AND g.goods.id=:goodsId", GoodsInStock.class);
        GoodsInStock goodsInStock = query
                .setParameter("stockId", stockId)
                .setParameter("goodsId", goodsId)
                .getSingleResult();
        LOG.debug("Found goods in a stock: {}", goodsId);
        return goodsInStock != null ? Optional.of(goodsInStock) : Optional.empty();
    }

    @Override
    public List<GoodsInStock> getAll() {
        LOG.debug("Call query for all goods in stocks");
        TypedQuery<GoodsInStock> query = entityManager.createQuery("SELECT g FROM goods_in_stocks g", GoodsInStock.class);
        return query.getResultList();
    }

    @Override
    public Optional<GoodsInStock> getById(long id) {
        LOG.debug("Call query for GoodsInStock(id = {})", id);
        GoodsInStock goodsInStock = entityManager.find(GoodsInStock.class, id);
        return goodsInStock != null ? Optional.of(goodsInStock) : Optional.empty();
    }

    @Override
    public Optional<GoodsInStock> save(GoodsInStock goodsInStock) {
        LOG.debug("Call query for saving goodsInStock");
        entityManager.persist(goodsInStock);
        return Optional.of(goodsInStock);
    }

    @Override
    public void update(GoodsInStock goodsInStock) {
        LOG.debug("Call query for updating GoodsInStock(id = {})", goodsInStock.getId());
        entityManager.merge(goodsInStock);
    }

    @Override
    public void delete(long id) {
        LOG.debug("Call query for deleting GoodsInStock(id = {})", id);
        GoodsInStock goodsInStock = entityManager.find(GoodsInStock.class, id);
        entityManager.remove(goodsInStock);
    }

    @Override
    public boolean exists(long id) {
        LOG.debug("Call query for checking if exists GoodsInStock(id = {})", id);
        GoodsInStock goodsInStock = entityManager.find(GoodsInStock.class, id);
        return goodsInStock != null;
    }
}
