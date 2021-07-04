package com.kpi.springlabs.backend.mappers.config;

import org.mapstruct.*;

@MapperConfig(
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.ERROR,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface BaseMapperConfig {
}
