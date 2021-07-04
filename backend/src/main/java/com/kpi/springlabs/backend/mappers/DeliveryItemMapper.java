package com.kpi.springlabs.backend.mappers;

import com.kpi.springlabs.backend.mappers.config.BaseMapperConfig;
import com.kpi.springlabs.backend.model.DeliveryItem;
import com.kpi.springlabs.backend.model.dto.DeliveryItemDto;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface DeliveryItemMapper extends BaseMapper<DeliveryItem, DeliveryItemDto> {
}
