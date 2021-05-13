package com.kpi.springlabs.backend.repository.jpa.impl;

import com.kpi.springlabs.backend.model.Goods;
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
public class GoodsJpaRepositoryImpl implements JpaBaseRepository<Goods> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Goods> getAll() {
        LOG.debug("Call query for all goods");
        TypedQuery<Goods> query = entityManager.createQuery("SELECT g FROM goods g", Goods.class);
        return query.getResultList();
    }

    @Override
    public Optional<Goods> getById(long id) {
        LOG.debug("Call query for certain Goods(id = {})", id);
        Goods goods = entityManager.find(Goods.class, id);
        return goods != null ? Optional.of(goods) : Optional.empty();
    }

    @Override
    public Optional<Goods> save(Goods goods) {
        LOG.debug("Call query for saving goods");
        entityManager.persist(goods);
        return Optional.of(goods);
    }

    @Override
    public void update(Goods goods) {
        LOG.debug("Call query for updating Goods(id = {})", goods.getId());
        entityManager.merge(goods);
    }

    @Override
    public void delete(long id) {
        LOG.debug("Call query for deleting Goods(id = {})", id);
        Goods goods = entityManager.find(Goods.class, id);
        entityManager.remove(goods);
    }

    @Override
    public boolean exists(long id) {
        LOG.debug("Call query for checking if exists Goods(id = {})", id);
        Goods goods = entityManager.find(Goods.class, id);
        return goods != null;
    }
}
