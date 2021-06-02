package com.kpi.springlabs.backend.model.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel(value = "Authentication request",
        description = "The response body in JSON format is used to authenticate the user.")
public class AuthenticationRequest {

    @NotNull(message = "Username cannot be null.")
    @Size(min = 2, max = 32, message = "The username must be from 2 to 32 characters long.")
    @ApiModelProperty(value = "Username", position = 1, required = true)
    private String username;

    @NotNull(message = "Password cannot be null.")
    @Size(min = 5, max = 50, message = "The password must be from 5 to 50 characters long.")
    @ApiModelProperty(value = "Password", position = 2, example = "12345", required = true)
    private String password;
}
