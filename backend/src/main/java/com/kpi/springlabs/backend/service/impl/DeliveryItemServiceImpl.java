package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.exception.ConflictException;
import com.kpi.springlabs.backend.exception.ObjectNotFoundException;
import com.kpi.springlabs.backend.model.DeliveryItem;
import com.kpi.springlabs.backend.model.DeliveryRequest;
import com.kpi.springlabs.backend.repository.jdbc.impl.DeliveryItemRepository;
import com.kpi.springlabs.backend.service.DeliveryItemService;
import com.kpi.springlabs.backend.service.DeliveryRequestService;
import com.kpi.springlabs.backend.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DeliveryItemServiceImpl implements DeliveryItemService {

    private final DeliveryItemRepository deliveryItemRepository;
    private final DeliveryRequestService deliveryRequestService;
    private final GoodsService goodsService;

    @Autowired
    public DeliveryItemServiceImpl(DeliveryItemRepository deliveryItemRepository, DeliveryRequestService deliveryRequestService,
                                   GoodsService goodsService) {
        this.deliveryItemRepository = deliveryItemRepository;
        this.deliveryRequestService = deliveryRequestService;
        this.goodsService = goodsService;
    }

    @Override
    public List<DeliveryItem> getDeliveryItems() {
        LOG.debug("Getting all delivery items");
        return deliveryItemRepository.getAll();
    }

    @Override
    public DeliveryItem getDeliveryItemById(long id) {
        LOG.debug("Getting DeliveryItem(id = {})", id);
        return deliveryItemRepository.getById(id)
                .orElseThrow(() -> {
                    LOG.error("DeliveryItem(id = {}) not found", id);
                    return new ObjectNotFoundException(String.format("DeliveryItem(id = %s) not found", id));
                });
    }

    @Override
    public DeliveryItem createDeliveryItem(DeliveryItem deliveryItem) {
        LOG.debug("Creating DeliveryItem {}", deliveryItem);

        DeliveryRequest deliveryRequest = deliveryItem.getDeliveryRequest();
        long deliveryRequestId = deliveryRequest.getId();

        long goodsId = deliveryItem.getGoods().getId();
        boolean existsGoods = goodsService.existsGoods(goodsId);
        if (!existsGoods) {
            LOG.error("Goods(id = {}) not found", goodsId);
            throw new ConflictException(String.format("Goods(id = %s) not found", goodsId));
        }
        boolean existsDeliveryRequest = deliveryRequestService.existsDeliveryRequest(deliveryRequestId);
        if (!existsDeliveryRequest) {
            LOG.error("Create DeliveryRequest(id = {}) as it does not exist", deliveryRequestId);
            deliveryRequestService.createDeliveryRequest(deliveryRequest);
        }

        return deliveryItemRepository.save(deliveryItem)
                .orElseThrow(() -> {
                    LOG.error("DeliveryItem {} cannot be created", deliveryItem);
                    return new ConflictException(String.format("DeliveryItem %s cannot be created", deliveryItem));
                });
    }

    @Override
    public void updateDeliveryItem(DeliveryItem deliveryItem) {
        LOG.debug("Updating DeliveryItem {}", deliveryItem);
        deliveryItemRepository.update(deliveryItem);
    }

    @Override
    public void deleteDeliveryItem(long id) {
        LOG.debug("Deleting DeliveryItem(id = {})", id);
        deliveryItemRepository.delete(id);
    }
}
