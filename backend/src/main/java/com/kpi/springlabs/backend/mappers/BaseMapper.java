package com.kpi.springlabs.backend.mappers;

import org.mapstruct.Mapping;

import java.util.List;

public interface BaseMapper<T, S> {

    S toDto(T entity);

    T toEntity(S dto);

    List<S> toDtoList(List<T> entities);

    @Mapping(source = "id", target = "id", ignore = true)
    T toEntityIgnoringId(S dto);
}
