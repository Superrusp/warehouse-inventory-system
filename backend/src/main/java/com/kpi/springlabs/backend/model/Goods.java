package com.kpi.springlabs.backend.model;

import com.kpi.springlabs.backend.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;


@Entity(name = Constants.TableNames.GOODS)
@SequenceGenerator(name = "base_sequence", sequenceName = "goods_sequence", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Goods extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 250)
    private String description;

    @Column(columnDefinition = "NUMBER(6,2)")
    private double price;

    public Goods(long id, String name, String description, double price) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
