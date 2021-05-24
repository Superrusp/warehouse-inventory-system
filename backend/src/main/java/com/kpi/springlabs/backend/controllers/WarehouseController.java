package com.kpi.springlabs.backend.controllers;

import com.kpi.springlabs.backend.model.Warehouse;
import com.kpi.springlabs.backend.service.WarehouseService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/warehouses")
@Api(value = "Warehouse Controller", tags = "warehouses")
@Slf4j
public class WarehouseController {

    private final WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @ApiOperation(value = "Get all warehouses", response = Warehouse.class, responseContainer = "List")
    @ApiResponse(code = 200, message = "Return warehouses")
    @GetMapping
    public List<Warehouse> loadAllWarehouses() {
        LOG.debug("Request all warehouses");
        return warehouseService.getWarehouses();
    }

    @ApiOperation(value = "Get warehouse by id", response = Warehouse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return warehouse"),
            @ApiResponse(code = 404, message = "Warehouse not found")
    })
    @GetMapping("/{id}")
    public Warehouse getWarehouse(@ApiParam(value = "Warehouse Id") @PathVariable long id) {
        LOG.debug("Request special warehouse");
        return warehouseService.getWarehouseById(id);
    }

    @ApiOperation(value = "Create warehouse")
    @ApiResponse(code = 201, message = "Warehouse created successfully")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public Warehouse createWarehouse(@ApiParam(value = "Warehouse") @RequestBody Warehouse warehouse) {
        LOG.debug("Request warehouse creation");
        return warehouseService.createWarehouse(warehouse);
    }

    @ApiOperation(value = "Update warehouse")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Warehouse updated successfully"),
            @ApiResponse(code = 404, message = "Warehouse not found")
    })
    @PutMapping
    public ResponseEntity<?> updateWarehouse(@ApiParam(value = "Warehouse") @RequestBody Warehouse warehouse) {
        LOG.debug("Request warehouse update");
        warehouseService.updateWarehouse(warehouse);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Delete warehouse by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Warehouse deleted successfully"),
            @ApiResponse(code = 404, message = "Warehouse not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWarehouse(@ApiParam(value = "Warehouse Id") @PathVariable long id) {
        LOG.debug("Request warehouse deletion");
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.ok().build();
    }
}
