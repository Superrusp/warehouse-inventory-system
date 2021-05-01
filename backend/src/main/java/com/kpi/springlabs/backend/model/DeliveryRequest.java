package com.kpi.springlabs.backend.model;

import com.kpi.springlabs.backend.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity(name = Constants.TableNames.DELIVERY_REQUESTS)
@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
public class DeliveryRequest extends BaseEntity {

    @ManyToMany
    @JoinTable(name = "delivery_request_goods",
            joinColumns = {@JoinColumn(name = "delivery_request_id")},
            inverseJoinColumns = {@JoinColumn(name = "goods_id")})
    private List<Goods> goods;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Column(name = "request_date")
    private LocalDate requestDate;

    @Column(name = "arrival_date")
    private LocalDate arrivalDate;

    public DeliveryRequest(long id, List<Goods> goods, LocalDate requestDate, LocalDate arrivalDate) {
        super(id);
        this.goods = goods;
        this.requestDate = requestDate;
        this.arrivalDate = arrivalDate;
    }
}
