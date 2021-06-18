package com.kpi.springlabs.backend.model.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@ApiModel(value = "Authentication response",
        description = "Response body in JSON format that contains access, refresh tokens and user information.")
public class AuthenticationResponse {

    @ApiModelProperty(value = "Access Token", position = 1)
    private final String accessToken;

    @ApiModelProperty(value = "Refresh Token", position = 2)
    private final String refreshToken;
}
