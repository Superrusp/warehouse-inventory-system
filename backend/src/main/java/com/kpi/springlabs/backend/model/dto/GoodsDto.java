package com.kpi.springlabs.backend.model.dto;

import com.kpi.springlabs.backend.validation.constraints.groups.ExcludeIdValidation;
import com.kpi.springlabs.backend.validation.constraints.groups.IncludeIdValidation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Goods Dto", description = "Represents a DTO for goods")
public class GoodsDto extends BaseDto {

    @NotBlank(message = "The name cannot be blank.",
            groups = {IncludeIdValidation.class, ExcludeIdValidation.class})
    @Size(max = 50, message = "The name must be no more than 50 characters.",
            groups = {IncludeIdValidation.class, ExcludeIdValidation.class})
    @ApiModelProperty(value = "Name", required = true)
    private String name;

    @Size(max = 250, message = "The description must be no more than 250 characters.",
            groups = {IncludeIdValidation.class, ExcludeIdValidation.class})
    @ApiModelProperty(value = "Description")
    private String description;

    @DecimalMin(value = "0.0", inclusive = false, groups = {IncludeIdValidation.class, ExcludeIdValidation.class},
            message = "The price must be more than 0.")
    @Digits(integer = 6, fraction = 2, groups = {IncludeIdValidation.class, ExcludeIdValidation.class},
            message = "The price has an incorrect format.")
    @ApiModelProperty(value = "Price")
    private double price;
}
