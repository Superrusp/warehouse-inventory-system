package com.kpi.springlabs.backend.repository.jpa.impl;

import com.kpi.springlabs.backend.model.Shop;
import com.kpi.springlabs.backend.repository.jpa.JpaBaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class ShopJpaRepositoryImpl implements JpaBaseRepository<Shop> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Shop> getAll() {
        LOG.debug("Call query for all shops");
        TypedQuery<Shop> query = entityManager.createQuery("SELECT s FROM shops s", Shop.class);
        return query.getResultList();
    }

    @Override
    public Optional<Shop> getById(long id) {
        LOG.debug("Call query for Shop(id = {})", id);
        Shop shop = entityManager.find(Shop.class, id);
        return shop != null ? Optional.of(shop) : Optional.empty();
    }

    @Override
    public Optional<Shop> save(Shop shop) {
        LOG.debug("Call query for saving shop");
        entityManager.persist(shop);
        return Optional.of(shop);
    }

    @Override
    public void update(Shop shop) {
        LOG.debug("Call query for updating Shop(id = {})", shop.getId());
        entityManager.merge(shop);
    }

    @Override
    public void delete(long id) {
        LOG.debug("Call query for deleting Shop(id = {})", id);
        Shop shop = entityManager.find(Shop.class, id);
        entityManager.remove(shop);
    }

    @Override
    public boolean exists(long id) {
        LOG.debug("Call query for checking if exists Shop(id = {})", id);
        Shop shop = entityManager.find(Shop.class, id);
        return shop != null;
    }
}
