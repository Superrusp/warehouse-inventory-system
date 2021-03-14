package com.kpi.springlabs.backend.controller;

import com.kpi.springlabs.backend.model.Warehouse;
import com.kpi.springlabs.backend.repository.impl.WarehouseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/warehouses")
@Slf4j
public class WarehouseController {

    private final WarehouseRepository warehouseRepository;

    @Autowired
    public WarehouseController(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @GetMapping
    public ResponseEntity<?> loadAllWarehouses() {
        LOG.debug("Request all warehouses");
        return ResponseEntity.ok(warehouseRepository.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWarehouse(@PathVariable long id) {
        LOG.debug("Request special warehouse");
        return ResponseEntity.ok(warehouseRepository.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> createWarehouse(@RequestBody Warehouse warehouse) {
        LOG.debug("Request warehouse creation");
        return ResponseEntity.ok(warehouseRepository.insert(warehouse));
    }

    @PutMapping
    public ResponseEntity<?> updateGoods(@RequestBody Warehouse warehouse) {
        LOG.debug("Request warehouse update");
        warehouseRepository.update(warehouse);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoods(@PathVariable long id) {
        LOG.debug("Request warehouse deletion");
        warehouseRepository.delete(id);
        return ResponseEntity.ok().build();
    }
}
