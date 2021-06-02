package com.kpi.springlabs.backend.model.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel(value = "Registration request",
        description = "The response body in JSON format is used to register a new user.")
public class RegistrationRequest {

    @NotNull(message = "Username cannot be null.")
    @Size(min = 2, max = 32, message = "The username must be from 2 to 32 characters long.")
    @ApiModelProperty(value = "Username", position = 1, example = "tester1", required = true)
    private String username;

    @Email(message = "Incorrect email format.")
    @Size(min = 6, max = 32, message = "The email must be from 6 to 32 characters long.")
    @ApiModelProperty(value = "Email for registration", position = 2, example = "name@test.com", required = true)
    private String email;

    @NotNull(message = "Password cannot be null.")
    @Size(min = 5, max = 50, message = "The password must be from 5 to 50 characters long.")
    @ApiModelProperty(value = "Password", position = 3, example = "12345", required = true)
    private String password;
}
