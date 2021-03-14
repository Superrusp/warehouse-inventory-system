package com.kpi.springlabs.backend.controller;

import com.kpi.springlabs.backend.model.DeliveryRequest;
import com.kpi.springlabs.backend.repository.impl.DeliveryRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery-requests")
@Slf4j
public class DeliveryRequestController {

    private final DeliveryRequestRepository deliveryRequestRepository;

    @Autowired
    public DeliveryRequestController(DeliveryRequestRepository deliveryRequestRepository) {
        this.deliveryRequestRepository = deliveryRequestRepository;
    }

    @GetMapping
    public ResponseEntity<?> loadAllDeliveryRequests() {
        LOG.debug("Request all delivery requests");
        return ResponseEntity.ok(deliveryRequestRepository.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDeliveryRequest(@PathVariable long id) {
        LOG.debug("Request special delivery request");
        return ResponseEntity.ok(deliveryRequestRepository.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> createDeliveryRequest(@RequestBody DeliveryRequest deliveryRequest) {
        LOG.debug("Request delivery request creation");
        return ResponseEntity.ok(deliveryRequestRepository.insert(deliveryRequest));
    }

    @PutMapping
    public ResponseEntity<?> updateDeliveryRequest(@RequestBody DeliveryRequest deliveryRequest) {
        LOG.debug("Request delivery request update");
        deliveryRequestRepository.update(deliveryRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeliveryRequest(@PathVariable long id) {
        LOG.debug("Request delivery request deletion");
        deliveryRequestRepository.delete(id);
        return ResponseEntity.ok().build();
    }
}
