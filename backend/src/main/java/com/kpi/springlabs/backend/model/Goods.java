package com.kpi.springlabs.backend.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class Goods extends BaseEntity {

    private String name;
    private String description;
    private double price;

    public Goods(long id, String name, String description, double price) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
