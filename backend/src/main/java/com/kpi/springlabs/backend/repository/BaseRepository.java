package com.kpi.springlabs.backend.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {

    List<T> getAll();

    Optional<T> getById(long id);

    Optional<T> insert(T entity);

    void update(T entity);

    void delete(long id);

}
