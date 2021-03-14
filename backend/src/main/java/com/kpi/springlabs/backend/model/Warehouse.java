package com.kpi.springlabs.backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class Warehouse extends BaseEntity {

    private String name;

    public Warehouse(long id, String name) {
        super(id);
        this.name = name;
    }
}
