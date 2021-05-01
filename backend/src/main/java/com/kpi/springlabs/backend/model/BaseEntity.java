package com.kpi.springlabs.backend.model;

import lombok.*;

import javax.persistence.*;

@MappedSuperclass
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
