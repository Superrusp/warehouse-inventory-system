package com.kpi.springlabs.backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class GoodsInShop extends BaseEntity {

    private Shop shop;
    private Goods goods;
    private int availableQuantity;

    public GoodsInShop(long id, Shop shop, Goods goods, int availableQuantity) {
        super(id);
        this.shop = shop;
        this.goods = goods;
        this.availableQuantity = availableQuantity;
    }
}
