package com.kpi.springlabs.backend.model.dto;

import com.kpi.springlabs.backend.validation.constraints.groups.IncludeIdValidation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public abstract class BaseDto {

    @Min(value = 1, message = "The id cannot be empty and less than 1.", groups = IncludeIdValidation.class)
    @ApiModelProperty(value = "Id", required = true)
    private long id;
}
