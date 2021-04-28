package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.DeliveryItem;

import java.util.List;

public interface DeliveryItemService {

    List<DeliveryItem> getDeliveryItems();

    DeliveryItem getDeliveryItemById(long id);

    DeliveryItem createDeliveryItem(DeliveryItem deliveryItem);

    void updateDeliveryItem(DeliveryItem deliveryItem);

    void deleteDeliveryItem(long id);
}
