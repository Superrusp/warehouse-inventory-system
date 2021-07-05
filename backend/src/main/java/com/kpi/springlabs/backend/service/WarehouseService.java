package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.dto.WarehouseDto;

import java.util.List;

public interface WarehouseService {

    List<WarehouseDto> getWarehouses();

    WarehouseDto getWarehouseById(long id);

    WarehouseDto createWarehouse(WarehouseDto warehouseDto);

    void updateWarehouse(WarehouseDto warehouseDto);

    void deleteWarehouse(long id);
}
