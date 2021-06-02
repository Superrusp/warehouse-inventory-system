package com.kpi.springlabs.backend.model.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@ApiModel(value = "Authentication response",
        description = "Response body in JSON format that contains token and user information.")
public class AuthenticationResponse {

    @ApiModelProperty(value = "Token", position = 1)
    private final String token;
}
