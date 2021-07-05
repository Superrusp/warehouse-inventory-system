package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.dto.DeliveryRequestDto;

import java.util.List;

public interface DeliveryRequestService {

    List<DeliveryRequestDto> getDeliveryRequests();

    DeliveryRequestDto getDeliveryRequestById(long id);

    List<DeliveryRequestDto> getDeliveryRequestsByGoodsId(long goodsId);

    DeliveryRequestDto createDeliveryRequest(DeliveryRequestDto deliveryRequestDto);

    void updateDeliveryRequest(DeliveryRequestDto deliveryRequestDto);

    void deleteDeliveryRequest(long id);

    boolean existsDeliveryRequest(long id);
}
