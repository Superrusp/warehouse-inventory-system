package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.exception.ConflictException;
import com.kpi.springlabs.backend.exception.ObjectNotFoundException;
import com.kpi.springlabs.backend.model.Warehouse;
import com.kpi.springlabs.backend.repository.jpa.impl.WarehouseJpaRepositoryImpl;
import com.kpi.springlabs.backend.service.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class WarehousesServiceImpl implements WarehouseService {

    private final WarehouseJpaRepositoryImpl warehouseRepository;

    @Autowired
    public WarehousesServiceImpl(WarehouseJpaRepositoryImpl warehouseRepository) {
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
    @Transactional
    public Warehouse createWarehouse(Warehouse warehouse) {
        LOG.debug("Creating Warehouse {}", warehouse);
        return warehouseRepository.save(warehouse)
                .orElseThrow(() -> {
                    LOG.error("Warehouse {} cannot be created", warehouse);
                    return new ConflictException(String.format("Warehouse %s cannot be created", warehouse));
                });
    }

    @Override
    @Transactional
    public void updateWarehouse(Warehouse warehouse) {
        LOG.debug("Updating Warehouse {}", warehouse);
        warehouseRepository.update(warehouse);
    }

    @Override
    @Transactional
    public void deleteWarehouse(long id) {
        LOG.debug("Deleting Warehouse(id = {})", id);
        warehouseRepository.delete(id);
    }
}
