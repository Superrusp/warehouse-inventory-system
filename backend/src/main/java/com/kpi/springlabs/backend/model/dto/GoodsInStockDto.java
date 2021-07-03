package com.kpi.springlabs.backend.model.dto;

import com.kpi.springlabs.backend.validation.constraints.groups.ExcludeIdValidation;
import com.kpi.springlabs.backend.validation.constraints.groups.IncludeIdValidation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Goods In Stock Dto", description = "Represents a DTO for goods in a warehouse")
public class GoodsInStockDto extends BaseDto {

    @Valid
    @NotNull(message = "Goods cannot be null.", groups = {IncludeIdValidation.class, ExcludeIdValidation.class})
    @ApiModelProperty(value = "Goods Dto", required = true)
    private GoodsDto goods;

    @Valid
    @NotNull(message = "Warehouse cannot be null.", groups = {IncludeIdValidation.class, ExcludeIdValidation.class})
    @ApiModelProperty(value = "Warehouse Dto", required = true)
    private WarehouseDto warehouse;

    @Min(value = 0, message = "The quantity cannot be less than 0.",
            groups = {IncludeIdValidation.class, ExcludeIdValidation.class})
    @Digits(integer = 5, fraction = 0,
            groups = {IncludeIdValidation.class, ExcludeIdValidation.class},
            message = "The quantity must contain no more than 5 digits.")
    @ApiModelProperty(value = "Available Quantity", required = true)
    private int availableQuantity;
}
