package com.kpi.springlabs.backend.mappers;

import com.kpi.springlabs.backend.mappers.config.BaseMapperConfig;
import com.kpi.springlabs.backend.model.Shop;
import com.kpi.springlabs.backend.model.dto.ShopDto;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface ShopMapper extends BaseMapper<Shop, ShopDto> {
}
