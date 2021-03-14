package com.kpi.springlabs.backend.controller;

import com.kpi.springlabs.backend.model.DeliveryItem;
import com.kpi.springlabs.backend.repository.impl.DeliveryItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery-items")
@Slf4j
public class DeliveryItemController {

    private final DeliveryItemRepository deliveryItemRepository;

    @Autowired
    public DeliveryItemController(DeliveryItemRepository deliveryItemRepository) {
        this.deliveryItemRepository = deliveryItemRepository;
    }

    @GetMapping
    public ResponseEntity<?> loadAllDeliveryItems() {
        LOG.debug("Request all delivery items");
        return ResponseEntity.ok(deliveryItemRepository.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDeliveryItem(@PathVariable long id) {
        LOG.debug("Request special delivery item");
        return ResponseEntity.ok(deliveryItemRepository.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> createDeliveryItem(@RequestBody DeliveryItem deliveryItem) {
        LOG.debug("Request delivery item creation");
        return ResponseEntity.ok(deliveryItemRepository.insert(deliveryItem));
    }

    @PutMapping
    public ResponseEntity<?> updateDeliveryItem(@RequestBody DeliveryItem deliveryItem) {
        LOG.debug("Request delivery item update");
        deliveryItemRepository.update(deliveryItem);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeliveryItem(@PathVariable long id) {
        LOG.debug("Request delivery item deletion");
        deliveryItemRepository.delete(id);
        return ResponseEntity.ok().build();
    }
}
