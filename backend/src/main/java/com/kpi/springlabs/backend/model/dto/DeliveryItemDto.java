package com.kpi.springlabs.backend.model.dto;

import com.kpi.springlabs.backend.validation.constraints.groups.ExcludeIdValidation;
import com.kpi.springlabs.backend.validation.constraints.groups.IncludeIdValidation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Delivery Item Dto", description = "Represents a DTO for a delivery item")
public class DeliveryItemDto extends BaseDto {

    @Valid
    @NotNull(message = "Goods cannot be null.", groups = {IncludeIdValidation.class, ExcludeIdValidation.class})
    @ApiModelProperty(value = "Goods Dto", required = true)
    private GoodsDto goods;

    @Valid
    @NotNull(message = "Delivery Request cannot be null.", groups = {IncludeIdValidation.class, ExcludeIdValidation.class})
    @ApiModelProperty(value = "Delivery Request Dto", required = true)
    private DeliveryRequestDto deliveryRequest;

    @NotBlank(message = "Delivery Status cannot be blank.", groups = {IncludeIdValidation.class, ExcludeIdValidation.class})
    @ApiModelProperty(value = "Delivery Status", required = true, allowableValues = "Shipped, Processing in progress")
    private String deliveryStatus;
}
