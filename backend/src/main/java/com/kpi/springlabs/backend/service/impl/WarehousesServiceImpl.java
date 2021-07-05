package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.exception.ConflictException;
import com.kpi.springlabs.backend.exception.ObjectNotFoundException;
import com.kpi.springlabs.backend.mappers.WarehouseMapper;
import com.kpi.springlabs.backend.model.Warehouse;
import com.kpi.springlabs.backend.model.dto.WarehouseDto;
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
    private final WarehouseMapper warehouseMapper;

    @Autowired
    public WarehousesServiceImpl(WarehouseJpaRepositoryImpl warehouseRepository, WarehouseMapper warehouseMapper) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseMapper = warehouseMapper;
    }

    @Override
    public List<WarehouseDto> getWarehouses() {
        LOG.debug("Getting all warehouses");
        List<Warehouse> warehouses = warehouseRepository.getAll();
        return warehouseMapper.toDtoList(warehouses);
    }

    @Override
    public WarehouseDto getWarehouseById(long id) {
        LOG.debug("Getting Warehouse(id = {})", id);
        Warehouse warehouse = warehouseRepository.getById(id)
                .orElseThrow(() -> {
                    LOG.error("Warehouse(id = {}) not found", id);
                    return new ObjectNotFoundException(String.format("Warehouse(id = %s) not found", id));
                });
        return warehouseMapper.toDto(warehouse);
    }

    @Override
    @Transactional
    public WarehouseDto createWarehouse(WarehouseDto warehouseDto) {
        Warehouse warehouse = warehouseMapper.toEntityIgnoringId(warehouseDto);
        LOG.debug("Creating Warehouse {}", warehouse);
        Warehouse createdWarehouse = warehouseRepository.save(warehouse)
                .orElseThrow(() -> {
                    LOG.error("Warehouse {} cannot be created", warehouse);
                    return new ConflictException(String.format("Warehouse %s cannot be created", warehouse));
                });
        return warehouseMapper.toDto(createdWarehouse);
    }

    @Override
    @Transactional
    public void updateWarehouse(WarehouseDto warehouseDto) {
        Warehouse warehouse = warehouseMapper.toEntity(warehouseDto);
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
