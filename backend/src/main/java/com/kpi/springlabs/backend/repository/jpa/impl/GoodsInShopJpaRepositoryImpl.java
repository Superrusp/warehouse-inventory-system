package com.kpi.springlabs.backend.repository.jpa.impl;

import com.kpi.springlabs.backend.model.GoodsInShop;
import com.kpi.springlabs.backend.repository.jpa.GoodsInShopJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class GoodsInShopJpaRepositoryImpl implements GoodsInShopJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GoodsInShop> getAllGoodsByShopId(long shopId) {
        LOG.debug("Call query for all goods in Shop(id = {})", shopId);
        TypedQuery<GoodsInShop> query = entityManager
                .createQuery("SELECT g FROM goods_in_shops g WHERE g.shop.id=:shopId", GoodsInShop.class);
        return query
                .setParameter("shopId", shopId)
                .getResultList();
    }

    @Override
    public Optional<GoodsInShop> getGoodsByIdAndShop(long shopId, long goodsId) {
        LOG.debug("Call query for Goods(id = {}) in Shop(id = {})", goodsId, shopId);
        TypedQuery<GoodsInShop> query = entityManager
                .createQuery("SELECT g FROM goods_in_shops g WHERE g.shop.id=:shopId AND g.goods.id=:goodsId", GoodsInShop.class);
        GoodsInShop goodsInShop = query
                .setParameter("shopId", shopId)
                .setParameter("goodsId", goodsId)
                .getSingleResult();
        LOG.debug("Found goods in a shop: {}", goodsId);
        return goodsInShop != null ? Optional.of(goodsInShop) : Optional.empty();
    }

    @Override
    public List<GoodsInShop> getAll() {
        LOG.debug("Call query for all goods in shops");
        TypedQuery<GoodsInShop> query = entityManager.createQuery("SELECT g FROM goods_in_shops g", GoodsInShop.class);
        return query.getResultList();
    }

    @Override
    public Optional<GoodsInShop> getById(long id) {
        LOG.debug("Call query for GoodsInShop(id = {})", id);
        GoodsInShop goodsInShop = entityManager.find(GoodsInShop.class, id);
        return goodsInShop != null ? Optional.of(goodsInShop) : Optional.empty();
    }

    @Override
    public Optional<GoodsInShop> save(GoodsInShop goodsInShop) {
        LOG.debug("Call query for saving goodsInShop");
        entityManager.persist(goodsInShop);
        return Optional.of(goodsInShop);
    }

    @Override
    public void update(GoodsInShop goodsInShop) {
        LOG.debug("Call query for updating GoodsInShop(id = {})", goodsInShop.getId());
        entityManager.merge(goodsInShop);
    }

    @Override
    public void delete(long id) {
        LOG.debug("Call query for deleting GoodsInShop(id = {})", id);
        GoodsInShop goodsInShop = entityManager.find(GoodsInShop.class, id);
        entityManager.remove(goodsInShop);
    }

    @Override
    public boolean exists(long id) {
        LOG.debug("Call query for checking if exists GoodsInShop(id = {})", id);
        GoodsInShop goodsInShop = entityManager.find(GoodsInShop.class, id);
        return goodsInShop != null;
    }
}
