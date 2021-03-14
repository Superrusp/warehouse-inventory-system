package com.kpi.springlabs.backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class Shop extends BaseEntity {

    private String name;

    public Shop(long id, String name) {
        super(id);
        this.name = name;
    }
}
