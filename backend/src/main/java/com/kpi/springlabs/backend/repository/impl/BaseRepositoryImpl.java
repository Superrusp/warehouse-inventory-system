package com.kpi.springlabs.backend.repository.impl;

import com.kpi.springlabs.backend.model.BaseEntity;
import com.kpi.springlabs.backend.repository.BaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public abstract class BaseRepositoryImpl<T extends BaseEntity> implements BaseRepository<T> {

    protected abstract List<T> getList();

    @Override
    public List<T> getAll() {
        LOG.debug("Getting all elements {} in {}", getList(), this.getClass().getName());
        return getList();
    }

    @Override
    public Optional<T> getById(long id) {
        LOG.debug("Getting element by id: {} in {}", id, this.getClass().getName());
        return getList().stream()
                .filter(element -> element.getId() == id)
                .findAny();
    }

    @Override
    public Optional<T> insert(T entity) {
        LOG.debug("Inserting element {} in {}", entity, this.getClass().getName());
        getList().add(entity);
        return Optional.of(entity);
    }

    @Override
    public void update(T entity) {
        LOG.debug("Updating element {} in {} ", entity, this.getClass().getName());
        Optional<T> outdatedElement = getList().stream()
                .filter(element -> element.getId() == entity.getId())
                .findFirst();
        LOG.debug("Outdated element: {}", outdatedElement);

        outdatedElement.ifPresent(element -> {
            getList().remove(element);
            getList().add(entity);
        });
    }

    @Override
    public void delete(long id) {
        LOG.debug("Deleting element by id {} in {}", id, this.getClass().getName());
        getList().removeIf(element -> element.getId() == id);
    }
}
