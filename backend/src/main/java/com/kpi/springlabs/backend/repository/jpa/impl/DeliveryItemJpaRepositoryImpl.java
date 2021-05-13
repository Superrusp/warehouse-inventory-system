package com.kpi.springlabs.backend.repository.jpa.impl;

import com.kpi.springlabs.backend.model.DeliveryItem;
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
public class DeliveryItemJpaRepositoryImpl implements JpaBaseRepository<DeliveryItem> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DeliveryItem> getAll() {
        LOG.debug("Call query for all delivery items");
        TypedQuery<DeliveryItem> query = entityManager.createQuery("SELECT d FROM delivery_items d", DeliveryItem.class);
        return query.getResultList();
    }

    @Override
    public Optional<DeliveryItem> getById(long id) {
        LOG.debug("Call query for DeliveryItem(id = {})", id);
        DeliveryItem deliveryItem = entityManager.find(DeliveryItem.class, id);
        return deliveryItem != null ? Optional.of(deliveryItem) : Optional.empty();
    }

    @Override
    public Optional<DeliveryItem> save(DeliveryItem deliveryItem) {
        LOG.debug("Call query for saving delivery item");
        entityManager.persist(deliveryItem);
        return Optional.of(deliveryItem);
    }

    @Override
    public void update(DeliveryItem deliveryItem) {
        LOG.debug("Call query for updating DeliveryItem(id = {})", deliveryItem.getId());
        entityManager.merge(deliveryItem);
    }

    @Override
    public void delete(long id) {
        LOG.debug("Call query for deleting DeliveryItem(id = {})", id);
        DeliveryItem deliveryItem = entityManager.find(DeliveryItem.class, id);
        entityManager.remove(deliveryItem);
    }

    @Override
    public boolean exists(long id) {
        LOG.debug("Call query for checking if exists DeliveryItem(id = {})", id);
        DeliveryItem deliveryItem = entityManager.find(DeliveryItem.class, id);
        return deliveryItem != null;
    }
}
