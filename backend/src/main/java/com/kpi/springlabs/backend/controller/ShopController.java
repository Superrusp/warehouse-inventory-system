package com.kpi.springlabs.backend.controller;

import com.kpi.springlabs.backend.model.Shop;
import com.kpi.springlabs.backend.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/shops")
@Slf4j
public class ShopController {

    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping
    public ResponseEntity<?> loadAllShops() {
        LOG.debug("Request all shops");
        return ResponseEntity.ok(shopService.getShops());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getShop(@PathVariable long id) {
        LOG.debug("Request special shop");
        return ResponseEntity.ok(shopService.getShopById(id));
    }

    @PostMapping
    public ResponseEntity<?> createShop(@RequestBody Shop shop) {
        LOG.debug("Request shop creation");
        return ResponseEntity.ok(shopService.createShop(shop));
    }

    @PutMapping
    public ResponseEntity<?> updateGoods(@RequestBody Shop shop) {
        LOG.debug("Request shop update");
        shopService.updateShop(shop);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoods(@PathVariable long id) {
        LOG.debug("Request shop deletion");
        shopService.deleteShop(id);
        return ResponseEntity.ok().build();
    }
}
