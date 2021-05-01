package com.kpi.springlabs.backend.model;

import com.kpi.springlabs.backend.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = Constants.TableNames.GOODS_IN_STOCKS)
@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class GoodsInStock extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @Column(name = "available_quantity")
    private int availableQuantity;

    public GoodsInStock(long id, Goods goods, Warehouse warehouse, int availableQuantity) {
        super(id);
        this.goods = goods;
        this.warehouse = warehouse;
        this.availableQuantity = availableQuantity;
    }
}
