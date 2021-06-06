package com.kpi.springlabs.backend.model.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ApiModel(value = "Authentication request",
        description = "The request body in JSON format is used to authenticate the user.")
public class AuthenticationRequest {

    @NotBlank(message = "Username cannot be blank.")
    @Size(min = 2, max = 32, message = "The username must be from 2 to 32 characters long.")
    @ApiModelProperty(value = "Username", position = 1, required = true)
    private String username;

    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 5, max = 50, message = "The password must be from 5 to 50 characters long.")
    @ApiModelProperty(value = "Password", position = 2, example = "12345", required = true)
    private String password;
}
