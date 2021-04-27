package com.kpi.springlabs.backend.repository.jdbc;

import com.kpi.springlabs.backend.model.DeliveryRequest;

import java.util.List;

public interface DeliveryRequestRepository extends BaseRepository<DeliveryRequest> {

    List<DeliveryRequest> getDeliveryRequestsByGoodsId(long goodsId);

    boolean existsGoodsInDeliveryRequest(long deliveryRequestId, long goodsId);
}
