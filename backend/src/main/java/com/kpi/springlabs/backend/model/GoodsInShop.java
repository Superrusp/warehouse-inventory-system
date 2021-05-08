package com.kpi.springlabs.backend.model;

import com.kpi.springlabs.backend.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = Constants.TableNames.GOODS_IN_SHOPS)
@SequenceGenerator(name = "base_sequence", sequenceName = "goods_in_shops_sequence", allocationSize = 1)
@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
public class GoodsInShop extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false, foreignKey = @ForeignKey(name = "FK_gish_shops"))
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "goods_id", nullable = false, foreignKey = @ForeignKey(name = "FK_gish_goods"))
    private Goods goods;

    @Column(nullable = false, columnDefinition = "NUMBER(5)")
    private int availableQuantity;

    public GoodsInShop(long id, Shop shop, Goods goods, int availableQuantity) {
        super(id);
        this.shop = shop;
        this.goods = goods;
        this.availableQuantity = availableQuantity;
    }
}
