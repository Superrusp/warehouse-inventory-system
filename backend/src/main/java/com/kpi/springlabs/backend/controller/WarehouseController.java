package com.kpi.springlabs.backend.controller;

import com.kpi.springlabs.backend.model.Warehouse;
import com.kpi.springlabs.backend.service.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/warehouses")
@Slf4j
public class WarehouseController {

    private final WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public ResponseEntity<?> loadAllWarehouses() {
        LOG.debug("Request all warehouses");
        return ResponseEntity.ok(warehouseService.getWarehouses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWarehouse(@PathVariable long id) {
        LOG.debug("Request special warehouse");
        return ResponseEntity.ok(warehouseService.getWarehouseById(id));
    }

    @PostMapping
    public ResponseEntity<?> createWarehouse(@RequestBody Warehouse warehouse) {
        LOG.debug("Request warehouse creation");
        return ResponseEntity.ok(warehouseService.createWarehouse(warehouse));
    }

    @PutMapping
    public ResponseEntity<?> updateGoods(@RequestBody Warehouse warehouse) {
        LOG.debug("Request warehouse update");
        warehouseService.updateWarehouse(warehouse);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoods(@PathVariable long id) {
        LOG.debug("Request warehouse deletion");
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.ok().build();
    }
}
