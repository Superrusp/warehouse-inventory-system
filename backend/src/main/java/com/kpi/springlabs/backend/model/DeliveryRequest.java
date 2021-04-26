package com.kpi.springlabs.backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class DeliveryRequest extends BaseEntity {

    private List<Goods> goods;
    private Warehouse warehouse;
    private Shop shop;
    private LocalDate requestDate;
    private LocalDate arrivalDate;

    public DeliveryRequest(long id, List<Goods> goods, LocalDate requestDate, LocalDate arrivalDate) {
        super(id);
        this.goods = goods;
        this.requestDate = requestDate;
        this.arrivalDate = arrivalDate;
    }
}
