package com.kpi.springlabs.backend.mappers;

import com.kpi.springlabs.backend.mappers.config.BaseMapperConfig;
import com.kpi.springlabs.backend.model.GoodsInStock;
import com.kpi.springlabs.backend.model.dto.GoodsInStockDto;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface GoodsInStockMapper extends BaseMapper<GoodsInStock, GoodsInStockDto> {
}
