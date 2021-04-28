package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.exception.ConflictException;
import com.kpi.springlabs.backend.exception.ObjectNotFoundException;
import com.kpi.springlabs.backend.model.DeliveryRequest;
import com.kpi.springlabs.backend.repository.jdbc.DeliveryRequestRepository;
import com.kpi.springlabs.backend.service.DeliveryRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DeliveryRequestServiceImpl implements DeliveryRequestService {

    private final DeliveryRequestRepository deliveryRequestRepository;

    @Autowired
    public DeliveryRequestServiceImpl(DeliveryRequestRepository deliveryRequestRepository) {
        this.deliveryRequestRepository = deliveryRequestRepository;
    }

    @Override
    public List<DeliveryRequest> getDeliveryRequests() {
        LOG.debug("Getting all delivery requests");
        return deliveryRequestRepository.getAll();
    }

    @Override
    public DeliveryRequest getDeliveryRequestById(long id) {
        LOG.debug("Getting DeliveryRequest(id = {})", id);
        return deliveryRequestRepository.getById(id)
                .orElseThrow(() -> {
                    LOG.error("DeliveryRequest(id = {}) not found", id);
                    return new ObjectNotFoundException(String.format("DeliveryRequest(id = %s) not found", id));
                });
    }

    @Override
    public List<DeliveryRequest> getDeliveryRequestsByGoodsId(long goodsId) {
        LOG.debug("Getting all delivery requests by Goods(id = {})", goodsId);
        return deliveryRequestRepository.getDeliveryRequestsByGoodsId(goodsId);
    }

    @Override
    public DeliveryRequest createDeliveryRequest(DeliveryRequest deliveryRequest) {
        LOG.debug("Creating DeliveryRequest {}", deliveryRequest);
        return deliveryRequestRepository.save(deliveryRequest)
                .orElseThrow(() -> {
                    LOG.error("DeliveryRequest {} cannot be created", deliveryRequest);
                    return new ConflictException(String.format("DeliveryRequest %s cannot be created", deliveryRequest));
                });
    }

    @Override
    public void updateDeliveryRequest(DeliveryRequest deliveryRequest) {
        LOG.debug("Updating DeliveryRequest {}", deliveryRequest);
        deliveryRequestRepository.update(deliveryRequest);
    }

    @Override
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
