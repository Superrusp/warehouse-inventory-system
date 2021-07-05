package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.dto.DeliveryItemDto;
import com.kpi.springlabs.backend.model.dto.response.DeliveryItemStatusResponse;

import java.util.List;

public interface DeliveryItemService {

    List<DeliveryItemDto> getDeliveryItems();

    DeliveryItemDto getDeliveryItemById(long id);

    List<DeliveryItemStatusResponse> getDeliveryItemByDeliveryStatus(String deliveryStatus);

    DeliveryItemDto createDeliveryItem(DeliveryItemDto deliveryItemDto);

    void updateDeliveryItem(DeliveryItemDto deliveryItemDto);

    void deleteDeliveryItem(long id);
}
