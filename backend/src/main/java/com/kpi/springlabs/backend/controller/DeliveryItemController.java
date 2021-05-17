package com.kpi.springlabs.backend.controller;

import com.kpi.springlabs.backend.aop.TrackExecutionTime;
import com.kpi.springlabs.backend.model.DeliveryItem;
import com.kpi.springlabs.backend.service.DeliveryItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/delivery-items")
@Slf4j
public class DeliveryItemController {

    @Autowired
    private final DeliveryItemService deliveryItemService;

    @Autowired
    public DeliveryItemController(DeliveryItemService deliveryItemService) {
        this.deliveryItemService = deliveryItemService;
    }

    @GetMapping
    public ResponseEntity<?> loadAllDeliveryItems() {
        LOG.debug("Request all delivery items");
        return ResponseEntity.ok(deliveryItemService.getDeliveryItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDeliveryItem(@PathVariable long id) {
        LOG.debug("Request special delivery item");
        return ResponseEntity.ok(deliveryItemService.getDeliveryItemById(id));
    }

    @GetMapping("/search")
    @TrackExecutionTime
    public ResponseEntity<?> getDeliveryItemByDeliveryStatus(@RequestParam String deliveryStatus) {
        LOG.debug("Request delivery items by delivery status");
        return ResponseEntity.ok(deliveryItemService.getDeliveryItemByDeliveryStatus(deliveryStatus));
    }

    @PostMapping
    public ResponseEntity<?> createDeliveryItem(@RequestBody DeliveryItem deliveryItem) {
        LOG.debug("Request delivery item creation");
        return ResponseEntity.ok(deliveryItemService.createDeliveryItem(deliveryItem));
    }

    @PutMapping
    public ResponseEntity<?> updateDeliveryItem(@RequestBody DeliveryItem deliveryItem) {
        LOG.debug("Request delivery item update");
        deliveryItemService.updateDeliveryItem(deliveryItem);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeliveryItem(@PathVariable long id) {
        LOG.debug("Request delivery item deletion");
        deliveryItemService.deleteDeliveryItem(id);
        return ResponseEntity.ok().build();
    }
}
