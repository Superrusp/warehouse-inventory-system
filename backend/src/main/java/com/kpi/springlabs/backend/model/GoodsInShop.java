package com.kpi.springlabs.backend.model;

import com.kpi.springlabs.backend.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = Constants.TableNames.GOODS_IN_SHOPS)
@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
public class GoodsInShop extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @Column(name = "available_quantity")
    private int availableQuantity;

    public GoodsInShop(long id, Shop shop, Goods goods, int availableQuantity) {
        super(id);
        this.shop = shop;
        this.goods = goods;
        this.availableQuantity = availableQuantity;
    }
}
