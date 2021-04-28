package com.kpi.springlabs.backend.controller;

import com.kpi.springlabs.backend.model.DeliveryRequest;
import com.kpi.springlabs.backend.service.DeliveryRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/delivery-requests")
@Slf4j
public class DeliveryRequestController {

    private final DeliveryRequestService deliveryRequestService;

    @Autowired
    public DeliveryRequestController(DeliveryRequestService deliveryRequestService) {
        this.deliveryRequestService = deliveryRequestService;
    }

    @GetMapping
    public ResponseEntity<?> loadAllDeliveryRequests() {
        LOG.debug("Request all delivery requests");
        return ResponseEntity.ok(deliveryRequestService.getDeliveryRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDeliveryRequest(@PathVariable long id) {
        LOG.debug("Request special delivery request");
        return ResponseEntity.ok(deliveryRequestService.getDeliveryRequestById(id));
    }

    @GetMapping("/goods")
    public ResponseEntity<?> getDeliveryRequestsOfGoods(@RequestParam long goodsId) {
        LOG.debug("Request delivery requests of goods");
        return ResponseEntity.ok(deliveryRequestService.getDeliveryRequestsByGoodsId(goodsId));
    }

    @PostMapping
    public ResponseEntity<?> createDeliveryRequest(@RequestBody DeliveryRequest deliveryRequest) {
        LOG.debug("Request delivery request creation");
        return ResponseEntity.ok(deliveryRequestService.createDeliveryRequest(deliveryRequest));
    }

    @PutMapping
    public ResponseEntity<?> updateDeliveryRequest(@RequestBody DeliveryRequest deliveryRequest) {
        LOG.debug("Request delivery request update");
        deliveryRequestService.updateDeliveryRequest(deliveryRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeliveryRequest(@PathVariable long id) {
        LOG.debug("Request delivery request deletion");
        deliveryRequestService.deleteDeliveryRequest(id);
        return ResponseEntity.ok().build();
    }
}
