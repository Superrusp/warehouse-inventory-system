package com.kpi.springlabs.backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class DeliveryItem extends BaseEntity {

    private Goods goods;
    private DeliveryRequest deliveryRequest;
    private String deliveryStatus;

    public DeliveryItem(long id, Goods goods, DeliveryRequest deliveryRequest, String deliveryStatus) {
        super(id);
        this.goods = goods;
        this.deliveryRequest = deliveryRequest;
        this.deliveryStatus = deliveryStatus;
    }
}
