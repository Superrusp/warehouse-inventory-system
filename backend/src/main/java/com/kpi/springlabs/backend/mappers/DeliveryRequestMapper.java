package com.kpi.springlabs.backend.mappers;

import com.kpi.springlabs.backend.mappers.config.BaseMapperConfig;
import com.kpi.springlabs.backend.model.DeliveryRequest;
import com.kpi.springlabs.backend.model.dto.DeliveryRequestDto;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface DeliveryRequestMapper extends BaseMapper<DeliveryRequest, DeliveryRequestDto> {
}
