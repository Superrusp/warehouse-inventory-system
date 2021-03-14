package com.kpi.springlabs.backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class GoodsInStock extends BaseEntity {

    private Goods goods;
    private Warehouse warehouse;
    private int availableQuantity;

    public GoodsInStock(long id, Goods goods, Warehouse warehouse, int availableQuantity) {
        super(id);
        this.goods = goods;
        this.warehouse = warehouse;
        this.availableQuantity = availableQuantity;
    }
}
