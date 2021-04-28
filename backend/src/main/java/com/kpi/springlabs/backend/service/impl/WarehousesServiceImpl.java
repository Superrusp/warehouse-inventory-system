package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.exception.ConflictException;
import com.kpi.springlabs.backend.exception.ObjectNotFoundException;
import com.kpi.springlabs.backend.model.Warehouse;
import com.kpi.springlabs.backend.repository.jdbc.impl.WarehouseRepository;
import com.kpi.springlabs.backend.service.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WarehousesServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    @Autowired
    public WarehousesServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public List<Warehouse> getWarehouses() {
        LOG.debug("Getting all warehouses");
        return warehouseRepository.getAll();
    }

    @Override
    public Warehouse getWarehouseById(long id) {
        LOG.debug("Getting Warehouse(id = {})", id);
        return warehouseRepository.getById(id)
                .orElseThrow(() -> {
                    LOG.error("Warehouse(id = {}) not found", id);
                    return new ObjectNotFoundException(String.format("Warehouse(id = %s) not found", id));
                });
    }

    @Override
    public Warehouse createWarehouse(Warehouse warehouse) {
        LOG.debug("Creating Warehouse {}", warehouse);
        return warehouseRepository.save(warehouse)
                .orElseThrow(() -> {
                    LOG.error("Warehouse {} cannot be created", warehouse);
                    return new ConflictException(String.format("Warehouse %s cannot be created", warehouse));
                });
    }

    @Override
    public void updateWarehouse(Warehouse warehouse) {
        LOG.debug("Updating Warehouse {}", warehouse);
        warehouseRepository.update(warehouse);
    }

    @Override
    public void deleteWarehouse(long id) {
        LOG.debug("Deleting Warehouse(id = {})", id);
        warehouseRepository.delete(id);
    }
}
