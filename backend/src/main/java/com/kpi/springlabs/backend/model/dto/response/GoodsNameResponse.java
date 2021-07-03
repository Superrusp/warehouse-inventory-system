package com.kpi.springlabs.backend.model.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(value = "Goods Name Response", description = "Response body in JSON format that contains id and name of goods.")
public class GoodsNameResponse {

    @ApiModelProperty(value = "Id", position = 1)
    private long id;

    @ApiModelProperty(value = "Name", position = 2)
    private String name;
}
