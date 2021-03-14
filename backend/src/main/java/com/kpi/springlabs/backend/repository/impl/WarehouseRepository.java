package com.kpi.springlabs.backend.repository.impl;

import com.kpi.springlabs.backend.model.Warehouse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class WarehouseRepository extends BaseRepositoryImpl<Warehouse> {

    private static long WAREHOUSE_COUNT;
    private static List<Warehouse> warehouseList;

    static {
        LOG.debug("Warehouse list initialization");
        warehouseList = new ArrayList<>();

        warehouseList.add(new Warehouse(++WAREHOUSE_COUNT, "Warehouse#1"));
        warehouseList.add(new Warehouse(++WAREHOUSE_COUNT, "Warehouse#2"));
        warehouseList.add(new Warehouse(++WAREHOUSE_COUNT, "Warehouse#3"));
        LOG.debug("Warehouse list: {}", warehouseList);
    }

    @Override
    protected List<Warehouse> getList() {
        return warehouseList;
    }
}
