package com.kpi.springlabs.backend.model;


import com.kpi.springlabs.backend.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

@Entity(name = Constants.TableNames.GOODS)
@Getter
@Setter
@NoArgsConstructor
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
