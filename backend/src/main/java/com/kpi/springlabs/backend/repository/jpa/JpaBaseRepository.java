package com.kpi.springlabs.backend.repository.jpa;

import java.util.List;
import java.util.Optional;

public interface JpaBaseRepository<T> {

    List<T> getAll();

    Optional<T> getById(long id);

    Optional<T> save(T entity);

    void update(T entity);

    void delete(long id);

    boolean exists(long id);
}
