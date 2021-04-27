package com.kpi.springlabs.backend.repository.jdbc;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {

    List<T> getAll();

    Optional<T> getById(long id);

    Optional<T> save(T entity);

    void update(T entity);

    void delete(long id);

    boolean exists(long id);
}
