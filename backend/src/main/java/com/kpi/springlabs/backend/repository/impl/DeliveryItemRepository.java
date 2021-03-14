package com.kpi.springlabs.backend.repository.impl;

import com.kpi.springlabs.backend.model.DeliveryItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class DeliveryItemRepository extends BaseRepositoryImpl<DeliveryItem> {

    private static long DELIVERY_ITEM_COUNT;
    private static List<DeliveryItem> deliveryItems;

    private final GoodsRepository goodsRepository;
    private final DeliveryRequestRepository deliveryRequestRepository;

    @Autowired
    public DeliveryItemRepository(GoodsRepository goodsRepository, DeliveryRequestRepository deliveryRequestRepository) {
        this.goodsRepository = goodsRepository;
        this.deliveryRequestRepository = deliveryRequestRepository;

        LOG.debug("Delivery items list initialization");
        deliveryItems = new ArrayList<>();

        DeliveryItem deliveryItem1 = new DeliveryItem(++DELIVERY_ITEM_COUNT,
                goodsRepository.getAll().stream().findFirst().get(),
                deliveryRequestRepository.getAll().stream().findFirst().get(),
                "Processing"
        );

        DeliveryItem deliveryItem2 = new DeliveryItem(++DELIVERY_ITEM_COUNT,
                goodsRepository.getAll().stream().findAny().get(),
                deliveryRequestRepository.getAll().stream().findAny().get(),
                "Shipped"
        );

        deliveryItems.add(deliveryItem1);
        deliveryItems.add(deliveryItem2);
        LOG.debug("Delivery items: {}", deliveryItems);
    }

    @Override
    protected List<DeliveryItem> getList() {
        return deliveryItems;
    }
}
