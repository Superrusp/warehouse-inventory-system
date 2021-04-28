package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.Warehouse;

import java.util.List;

public interface WarehouseService {

    List<Warehouse> getWarehouses();

    Warehouse getWarehouseById(long id);

    Warehouse createWarehouse(Warehouse warehouse);

    void updateWarehouse(Warehouse warehouse);

    void deleteWarehouse(long id);
}
