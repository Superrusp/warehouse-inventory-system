package com.kpi.springlabs.backend.model;

import com.kpi.springlabs.backend.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = Constants.TableNames.DELIVERY_ITEMS)
@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
public class DeliveryItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @ManyToOne
    @JoinColumn(name = "delivery_request_id")
    private DeliveryRequest deliveryRequest;

    @Column(name = "delivery_status")
    private String deliveryStatus;

    public DeliveryItem(long id, Goods goods, DeliveryRequest deliveryRequest, String deliveryStatus) {
        super(id);
        this.goods = goods;
        this.deliveryRequest = deliveryRequest;
        this.deliveryStatus = deliveryStatus;
    }
}
