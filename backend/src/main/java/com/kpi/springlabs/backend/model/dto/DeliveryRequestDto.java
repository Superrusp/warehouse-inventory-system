package com.kpi.springlabs.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kpi.springlabs.backend.validation.constraints.groups.ExcludeIdValidation;
import com.kpi.springlabs.backend.validation.constraints.groups.FutureOrPresentDateValidation;
import com.kpi.springlabs.backend.validation.constraints.groups.IncludeIdValidation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Delivery Request Dto", description = "Represents a DTO for a delivery request")
public class DeliveryRequestDto extends BaseDto {

    @Valid
    @NotEmpty(message = "Goods cannot be empty.", groups = {IncludeIdValidation.class, ExcludeIdValidation.class})
    @ApiModelProperty(value = "Goods Dto", required = true)
    private Set<GoodsDto> goods;

    @Valid
    @ApiModelProperty(value = "Shop Dto")
    private ShopDto shop;

    @Valid
    @ApiModelProperty(value = "Warehouse Dto")
    private WarehouseDto warehouse;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "The request date cannot be in the past.", groups = FutureOrPresentDateValidation.class)
    @NotNull(message = "The request date cannot be null.", groups = {IncludeIdValidation.class, ExcludeIdValidation.class})
    @ApiModelProperty(value = "Request Date", required = true)
    private LocalDate requestDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "The arrival date cannot be in the past.", groups = FutureOrPresentDateValidation.class)
    @NotNull(message = "The arrival date cannot be null.", groups = {IncludeIdValidation.class, ExcludeIdValidation.class})
    @ApiModelProperty(value = "Arrival Date", required = true)
    private LocalDate arrivalDate;
}
