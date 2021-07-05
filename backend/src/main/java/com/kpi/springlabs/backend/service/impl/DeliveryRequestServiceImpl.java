package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.exception.ConflictException;
import com.kpi.springlabs.backend.exception.ObjectNotFoundException;
import com.kpi.springlabs.backend.mappers.DeliveryRequestMapper;
import com.kpi.springlabs.backend.model.DeliveryRequest;
import com.kpi.springlabs.backend.model.dto.DeliveryRequestDto;
import com.kpi.springlabs.backend.repository.jpa.DeliveryRequestJpaRepository;
import com.kpi.springlabs.backend.service.DeliveryRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class DeliveryRequestServiceImpl implements DeliveryRequestService {

    private final DeliveryRequestJpaRepository deliveryRequestRepository;
    private final DeliveryRequestMapper deliveryRequestMapper;

    @Autowired
    public DeliveryRequestServiceImpl(DeliveryRequestJpaRepository deliveryRequestRepository,
                                      DeliveryRequestMapper deliveryRequestMapper) {
        this.deliveryRequestRepository = deliveryRequestRepository;
        this.deliveryRequestMapper = deliveryRequestMapper;
    }

    @Override
    public List<DeliveryRequestDto> getDeliveryRequests() {
        LOG.debug("Getting all delivery requests");
        List<DeliveryRequest> deliveryRequests = deliveryRequestRepository.getAll();
        return deliveryRequestMapper.toDtoList(deliveryRequests);
    }

    @Override
    public DeliveryRequestDto getDeliveryRequestById(long id) {
        LOG.debug("Getting DeliveryRequest(id = {})", id);
        DeliveryRequest deliveryRequest = deliveryRequestRepository.getById(id)
                .orElseThrow(() -> {
                    LOG.error("DeliveryRequest(id = {}) not found", id);
                    return new ObjectNotFoundException(String.format("DeliveryRequest(id = %s) not found", id));
                });
        return deliveryRequestMapper.toDto(deliveryRequest);
    }

    @Override
    public List<DeliveryRequestDto> getDeliveryRequestsByGoodsId(long goodsId) {
        LOG.debug("Getting all delivery requests by Goods(id = {})", goodsId);
        List<DeliveryRequest> deliveryRequests = deliveryRequestRepository.getDeliveryRequestsByGoodsId(goodsId);
        return deliveryRequestMapper.toDtoList(deliveryRequests);
    }

    @Override
    @Transactional
    public DeliveryRequestDto createDeliveryRequest(DeliveryRequestDto deliveryRequestDto) {
        DeliveryRequest deliveryRequest = deliveryRequestMapper.toEntityIgnoringId(deliveryRequestDto);
        LOG.debug("Creating DeliveryRequest {}", deliveryRequest);
        DeliveryRequest createdDeliveryRequest = deliveryRequestRepository.save(deliveryRequest)
                .orElseThrow(() -> {
                    LOG.error("DeliveryRequest {} cannot be created", deliveryRequest);
                    return new ConflictException(String.format("DeliveryRequest %s cannot be created", deliveryRequest));
                });
        return deliveryRequestMapper.toDto(createdDeliveryRequest);
    }

    @Override
    @Transactional
    public void updateDeliveryRequest(DeliveryRequestDto deliveryRequestDto) {
        DeliveryRequest deliveryRequest = deliveryRequestMapper.toEntity(deliveryRequestDto);
        LOG.debug("Updating DeliveryRequest {}", deliveryRequest);
        deliveryRequestRepository.update(deliveryRequest);
    }

    @Override
    @Transactional
    public void deleteDeliveryRequest(long id) {
        LOG.debug("Deleting DeliveryRequest(id = {})", id);
        deliveryRequestRepository.delete(id);
    }

    @Override
    public boolean existsDeliveryRequest(long id) {
        LOG.debug("Checking if delivery request exists");
        return deliveryRequestRepository.exists(id);
    }
}
