package com.kpi.springlabs.backend.repository.jpa.impl;

import com.kpi.springlabs.backend.model.Warehouse;
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
public class WarehouseJpaRepositoryImpl implements JpaBaseRepository<Warehouse> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Warehouse> getAll() {
        LOG.debug("Call query for all warehouses");
        TypedQuery<Warehouse> query = entityManager.createQuery("SELECT w FROM warehouses w", Warehouse.class);
        return query.getResultList();
    }

    @Override
    public Optional<Warehouse> getById(long id) {
        LOG.debug("Call query for Warehouse(id = {})", id);
        Warehouse warehouse = entityManager.find(Warehouse.class, id);
        return warehouse != null ? Optional.of(warehouse) : Optional.empty();
    }

    @Override
    public Optional<Warehouse> save(Warehouse warehouse) {
        LOG.debug("Call query for saving warehouse");
        entityManager.persist(warehouse);
        return Optional.of(warehouse);
    }

    @Override
    public void update(Warehouse warehouse) {
        LOG.debug("Call query for updating Warehouse(id = {})", warehouse.getId());
        entityManager.merge(warehouse);
    }

    @Override
    public void delete(long id) {
        LOG.debug("Call query for deleting Warehouse(id = {})", id);
        Warehouse warehouse = entityManager.find(Warehouse.class, id);
        entityManager.remove(warehouse);
    }

    @Override
    public boolean exists(long id) {
        LOG.debug("Call query for checking if exists Warehouse(id = {})", id);
        Warehouse warehouse = entityManager.find(Warehouse.class, id);
        return warehouse != null;
    }
}
