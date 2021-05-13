package com.kpi.springlabs.backend.repository.jpa;

import com.kpi.springlabs.backend.model.DeliveryRequest;

import java.util.List;

public interface DeliveryRequestJpaRepository extends JpaBaseRepository<DeliveryRequest> {

    List<DeliveryRequest> getDeliveryRequestsByGoodsId(long goodsId);
}
