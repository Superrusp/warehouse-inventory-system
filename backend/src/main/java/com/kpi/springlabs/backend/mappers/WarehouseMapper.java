package com.kpi.springlabs.backend.mappers;

import com.kpi.springlabs.backend.mappers.config.BaseMapperConfig;
import com.kpi.springlabs.backend.model.Warehouse;
import com.kpi.springlabs.backend.model.dto.WarehouseDto;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface WarehouseMapper extends BaseMapper<Warehouse, WarehouseDto> {
}
