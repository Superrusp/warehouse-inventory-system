package com.kpi.springlabs.backend.repository.jpa.impl;

import com.kpi.springlabs.backend.model.DeliveryRequest;
import com.kpi.springlabs.backend.repository.jpa.DeliveryRequestJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class DeliveryRequestJpaRepositoryImpl implements DeliveryRequestJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DeliveryRequest> getDeliveryRequestsByGoodsId(long goodsId) {
        LOG.debug("Call query for delivery requests with Goods(id = {})", goodsId);
        TypedQuery<DeliveryRequest> query = entityManager
                .createQuery("SELECT dr FROM delivery_requests dr JOIN dr.goods g ON g.id=:goodsId", DeliveryRequest.class);
        return query
                .setParameter("goodsId", goodsId)
                .getResultList();
    }

    @Override
    public List<DeliveryRequest> getAll() {
        LOG.debug("Call query for all delivery requests");
        TypedQuery<DeliveryRequest> query = entityManager.createQuery("SELECT d FROM delivery_requests d", DeliveryRequest.class);
        return query.getResultList();
    }

    @Override
    public Optional<DeliveryRequest> getById(long id) {
        LOG.debug("Call query for DeliveryRequest(id = {})", id);
        DeliveryRequest deliveryRequest = entityManager.find(DeliveryRequest.class, id);
        return deliveryRequest != null ? Optional.of(deliveryRequest) : Optional.empty();
    }

    @Override
    public Optional<DeliveryRequest> save(DeliveryRequest deliveryRequest) {
        LOG.debug("Call query for saving delivery request");
        entityManager.persist(deliveryRequest);
        return Optional.of(deliveryRequest);
    }

    @Override
    public void update(DeliveryRequest deliveryRequest) {
        LOG.debug("Call query for updating DeliveryRequest(id = {})", deliveryRequest.getId());
        entityManager.merge(deliveryRequest);
    }

    @Override
    public void delete(long id) {
        LOG.debug("Call query for deleting DeliveryRequest(id = {})", id);
        DeliveryRequest deliveryRequest = entityManager.find(DeliveryRequest.class, id);
        entityManager.remove(deliveryRequest);
    }

    @Override
    public boolean exists(long id) {
        LOG.debug("Call query for checking if exists DeliveryRequest(id = {})", id);
        DeliveryRequest deliveryRequest = entityManager.find(DeliveryRequest.class, id);
        return deliveryRequest != null;
    }
}
