package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.DeliveryRequest;

import java.util.List;

public interface DeliveryRequestService {

    List<DeliveryRequest> getDeliveryRequests();

    DeliveryRequest getDeliveryRequestById(long id);

    List<DeliveryRequest> getDeliveryRequestsByGoodsId(long goodsId);

    DeliveryRequest createDeliveryRequest(DeliveryRequest deliveryRequest);

    void updateDeliveryRequest(DeliveryRequest deliveryRequest);

    void deleteDeliveryRequest(long id);

    boolean existsDeliveryRequest(long id);
}
