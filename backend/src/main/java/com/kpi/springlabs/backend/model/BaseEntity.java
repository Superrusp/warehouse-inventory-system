package com.kpi.springlabs.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public abstract class BaseEntity {

    private final long id;
}
