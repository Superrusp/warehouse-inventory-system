package com.kpi.springlabs.backend.model.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(value = "Goods Name Response", description = "Response body in JSON format that contains id and name of goods.")
public class DeliveryItemStatusResponse {

    @ApiModelProperty(value = "Id", position = 1)
    private long id;

    @ApiModelProperty(value = "Delivery Status", position = 2, allowableValues = "Shipped, Processing in progress")
    private String deliveryStatus;
}
