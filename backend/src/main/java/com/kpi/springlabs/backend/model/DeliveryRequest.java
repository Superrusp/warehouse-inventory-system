package com.kpi.springlabs.backend.model;

import com.kpi.springlabs.backend.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity(name = Constants.TableNames.DELIVERY_REQUESTS)
@SequenceGenerator(name = "base_sequence", sequenceName = "delivery_requests_sequence", allocationSize = 1)
@Check(constraints = "(shop_id IS NOT NULL AND warehouse_id IS NULL) OR (warehouse_id IS NOT NULL and shop_id IS NULL)")
@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
public class DeliveryRequest extends BaseEntity {

    @ManyToMany
    @JoinTable(name = "delivery_request_goods",
            joinColumns = {@JoinColumn(name = "delivery_request_id", foreignKey = @ForeignKey(name = "FK_drg_delivery_requests"))},
            inverseJoinColumns = {@JoinColumn(name = "goods_id", foreignKey = @ForeignKey(name = "FK_drg_goods"))})
    private Set<Goods> goods;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", foreignKey = @ForeignKey(name = "FK_dr_warehouses"))
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "shop_id", foreignKey = @ForeignKey(name = "FK_dr_shops"))
    private Shop shop;

    @Column(nullable = false)
    private LocalDate requestDate;

    @Column(nullable = false)
    private LocalDate arrivalDate;

    public DeliveryRequest(long id, Set<Goods> goods, LocalDate requestDate, LocalDate arrivalDate) {
        super(id);
        this.goods = goods;
        this.requestDate = requestDate;
        this.arrivalDate = arrivalDate;
    }
}
