package com.kpi.springlabs.backend.model.dto;

import com.kpi.springlabs.backend.validation.constraints.groups.ExcludeIdValidation;
import com.kpi.springlabs.backend.validation.constraints.groups.IncludeIdValidation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Shop Dto", description = "Represents a DTO for a shop")
public class ShopDto extends BaseDto {

    @NotBlank(message = "The name cannot be blank.",
            groups = {IncludeIdValidation.class, ExcludeIdValidation.class})
    @Size(max = 50, message = "The name must be no more than 50 characters.",
            groups = {IncludeIdValidation.class, ExcludeIdValidation.class})
    @ApiModelProperty(value = "Name", required = true)
    private String name;
}
