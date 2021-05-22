package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.aop.TrackExecutionTime;
import com.kpi.springlabs.backend.exception.ConflictException;
import com.kpi.springlabs.backend.exception.ObjectNotFoundException;
import com.kpi.springlabs.backend.model.DeliveryItem;
import com.kpi.springlabs.backend.model.dto.DeliveryItemDto;
import com.kpi.springlabs.backend.repository.jpa.DeliveryItemJpaRepository;
import com.kpi.springlabs.backend.service.DeliveryItemService;
import com.kpi.springlabs.backend.service.DeliveryRequestService;
import com.kpi.springlabs.backend.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class DeliveryItemServiceImpl implements DeliveryItemService {

    private final DeliveryItemJpaRepository deliveryItemRepository;
    private final DeliveryRequestService deliveryRequestService;
    private final GoodsService goodsService;

    @Autowired
    public DeliveryItemServiceImpl(DeliveryItemJpaRepository deliveryItemRepository, DeliveryRequestService deliveryRequestService,
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
    @TrackExecutionTime
    public List<DeliveryItemDto> getDeliveryItemByDeliveryStatus(String deliveryStatus) {
        LOG.debug("Getting DeliveryItem(deliveryStatus = {})", deliveryStatus);
        return deliveryItemRepository.findDeliveryItemsByDeliveryStatus(deliveryStatus);
    }

    @Override
    @Transactional
    public DeliveryItem createDeliveryItem(DeliveryItem deliveryItem) {
        LOG.debug("Creating DeliveryItem {}", deliveryItem);
        return deliveryItemRepository.save(deliveryItem)
                .orElseThrow(() -> {
                    LOG.error("DeliveryItem {} cannot be created", deliveryItem);
                    return new ConflictException(String.format("DeliveryItem %s cannot be created", deliveryItem));
                });
    }

    @Override
    @Transactional
    public void updateDeliveryItem(DeliveryItem deliveryItem) {
        LOG.debug("Updating DeliveryItem {}", deliveryItem);
        deliveryItemRepository.update(deliveryItem);
    }

    @Override
    @Transactional
    public void deleteDeliveryItem(long id) {
        LOG.debug("Deleting DeliveryItem(id = {})", id);
        deliveryItemRepository.delete(id);
    }
}
