package com.kpi.springlabs.backend.mappers;

import com.kpi.springlabs.backend.mappers.config.BaseMapperConfig;
import com.kpi.springlabs.backend.model.Goods;
import com.kpi.springlabs.backend.model.dto.GoodsDto;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface GoodsMapper extends BaseMapper<Goods, GoodsDto> {
}
