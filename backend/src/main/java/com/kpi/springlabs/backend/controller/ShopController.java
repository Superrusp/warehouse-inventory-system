package com.kpi.springlabs.backend.controller;

import com.kpi.springlabs.backend.model.Shop;
import com.kpi.springlabs.backend.repository.impl.ShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shops")
@Slf4j
public class ShopController {

    private final ShopRepository shopRepository;

    @Autowired
    public ShopController(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @GetMapping
    public ResponseEntity<?> loadAllShops() {
        LOG.debug("Request all shops");
        return ResponseEntity.ok(shopRepository.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getShop(@PathVariable long id) {
        LOG.debug("Request special shop");
        return ResponseEntity.ok(shopRepository.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> createShop(@RequestBody Shop shop) {
        LOG.debug("Request shop creation");
        return ResponseEntity.ok(shopRepository.insert(shop));
    }

    @PutMapping
    public ResponseEntity<?> updateGoods(@RequestBody Shop shop) {
        LOG.debug("Request shop update");
        shopRepository.update(shop);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoods(@PathVariable long id) {
        LOG.debug("Request shop deletion");
        shopRepository.delete(id);
        return ResponseEntity.ok().build();
    }
}
