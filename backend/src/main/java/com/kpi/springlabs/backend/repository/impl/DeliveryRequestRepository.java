package com.kpi.springlabs.backend.repository.impl;

import com.kpi.springlabs.backend.model.DeliveryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class DeliveryRequestRepository extends BaseRepositoryImpl<DeliveryRequest> {

    private static long DELIVERY_REQUEST_COUNT;
    private static List<DeliveryRequest> deliveryRequestList;

    private final GoodsRepository goodsRepository;
    private final ShopRepository shopRepository;

    @Autowired
    public DeliveryRequestRepository(GoodsRepository goodsRepository, ShopRepository warehouseRepository) {
        this.goodsRepository = goodsRepository;
        this.shopRepository = warehouseRepository;

        LOG.debug("Delivery request list initialization");
        deliveryRequestList = new ArrayList<>();

        DeliveryRequest deliveryRequest1 = new DeliveryRequest(++DELIVERY_REQUEST_COUNT,
                goodsRepository.getList(),
                shopRepository.getList().stream().findFirst().get(),
                LocalDate.now(),
                LocalDate.now().plusDays(2)
        );

        DeliveryRequest deliveryRequest2 = new DeliveryRequest(++DELIVERY_REQUEST_COUNT,
                goodsRepository.getList(),
                shopRepository.getList().stream().findAny().get(),
                LocalDate.now(),
                LocalDate.now().plusDays(5));

        deliveryRequestList.add(deliveryRequest1);
        deliveryRequestList.add(deliveryRequest2);
        LOG.debug("Delivery request list: {}", deliveryRequestList);
    }

    @Override
    protected List<DeliveryRequest> getList() {
        return deliveryRequestList;
    }
}
