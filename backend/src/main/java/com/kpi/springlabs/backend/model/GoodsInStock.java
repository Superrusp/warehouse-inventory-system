package com.kpi.springlabs.backend.model;

import com.kpi.springlabs.backend.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = Constants.TableNames.GOODS_IN_STOCKS)
@SequenceGenerator(name = "base_sequence", sequenceName = "goods_in_stocks_sequence", allocationSize = 1)
@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class GoodsInStock extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "goods_id", nullable = false, foreignKey = @ForeignKey(name = "FK_gist_goods"))
    private Goods goods;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false, foreignKey = @ForeignKey(name = "FK_gist_warehouses"))
    private Warehouse warehouse;

    @Column(nullable = false, columnDefinition = "NUMBER(5)")
    private int availableQuantity;

    public GoodsInStock(long id, Goods goods, Warehouse warehouse, int availableQuantity) {
        super(id);
        this.goods = goods;
        this.warehouse = warehouse;
        this.availableQuantity = availableQuantity;
    }
}
