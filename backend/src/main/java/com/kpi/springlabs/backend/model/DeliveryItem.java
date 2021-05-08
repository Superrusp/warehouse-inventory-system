package com.kpi.springlabs.backend.model;

import com.kpi.springlabs.backend.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = Constants.TableNames.DELIVERY_ITEMS)
@SequenceGenerator(name = "base_sequence", sequenceName = "delivery_items_sequence", allocationSize = 1)
@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
public class DeliveryItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "goods_id", nullable = false, foreignKey = @ForeignKey(name = "FK_di_goods"))
    private Goods goods;

    @ManyToOne
    @JoinColumn(name = "delivery_request_id", nullable = false, foreignKey = @ForeignKey(name = "FK_di_delivery_requests"))
    private DeliveryRequest deliveryRequest;

    @Column(nullable = false, length = 30)
    private String deliveryStatus;

    public DeliveryItem(long id, Goods goods, DeliveryRequest deliveryRequest, String deliveryStatus) {
        super(id);
        this.goods = goods;
        this.deliveryRequest = deliveryRequest;
        this.deliveryStatus = deliveryStatus;
    }
}
