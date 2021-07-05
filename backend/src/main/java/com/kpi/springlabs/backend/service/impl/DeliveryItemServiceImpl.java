package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.aop.TrackExecutionTime;
import com.kpi.springlabs.backend.exception.ConflictException;
import com.kpi.springlabs.backend.exception.ObjectNotFoundException;
import com.kpi.springlabs.backend.mappers.DeliveryItemMapper;
import com.kpi.springlabs.backend.model.DeliveryItem;
import com.kpi.springlabs.backend.model.dto.DeliveryItemDto;
import com.kpi.springlabs.backend.model.dto.response.DeliveryItemStatusResponse;
import com.kpi.springlabs.backend.repository.jpa.DeliveryItemJpaRepository;
import com.kpi.springlabs.backend.service.DeliveryItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class DeliveryItemServiceImpl implements DeliveryItemService {

    private final DeliveryItemJpaRepository deliveryItemRepository;
    private final DeliveryItemMapper deliveryItemMapper;

    @Autowired
    public DeliveryItemServiceImpl(DeliveryItemJpaRepository deliveryItemRepository,
                                   DeliveryItemMapper deliveryItemMapper) {
        this.deliveryItemRepository = deliveryItemRepository;
        this.deliveryItemMapper = deliveryItemMapper;
    }

    @Override
    public List<DeliveryItemDto> getDeliveryItems() {
        LOG.debug("Getting all delivery items");
        List<DeliveryItem> deliveryItems = deliveryItemRepository.getAll();
        return deliveryItemMapper.toDtoList(deliveryItems);
    }

    @Override
    public DeliveryItemDto getDeliveryItemById(long id) {
        LOG.debug("Getting DeliveryItem(id = {})", id);
        DeliveryItem deliveryItem = deliveryItemRepository.getById(id)
                .orElseThrow(() -> {
                    LOG.error("DeliveryItem(id = {}) not found", id);
                    return new ObjectNotFoundException(String.format("DeliveryItem(id = %s) not found", id));
                });
        return deliveryItemMapper.toDto(deliveryItem);
    }

    @Override
    @TrackExecutionTime
    public List<DeliveryItemStatusResponse> getDeliveryItemByDeliveryStatus(String deliveryStatus) {
        LOG.debug("Getting DeliveryItem(deliveryStatus = {})", deliveryStatus);
        return deliveryItemRepository.findDeliveryItemsByDeliveryStatus(deliveryStatus);
    }

    @Override
    @Transactional
    public DeliveryItemDto createDeliveryItem(DeliveryItemDto deliveryItemDto) {
        DeliveryItem deliveryItem = deliveryItemMapper.toEntityIgnoringId(deliveryItemDto);
        LOG.debug("Creating DeliveryItem {}", deliveryItem);
        DeliveryItem createdDeliveryItem = deliveryItemRepository.save(deliveryItem)
                .orElseThrow(() -> {
                    LOG.error("DeliveryItem {} cannot be created", deliveryItem);
                    return new ConflictException(String.format("DeliveryItem %s cannot be created", deliveryItem));
                });
        return deliveryItemMapper.toDto(createdDeliveryItem);
    }

    @Override
    @Transactional
    public void updateDeliveryItem(DeliveryItemDto deliveryItemDto) {
        DeliveryItem deliveryItem = deliveryItemMapper.toEntity(deliveryItemDto);
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
