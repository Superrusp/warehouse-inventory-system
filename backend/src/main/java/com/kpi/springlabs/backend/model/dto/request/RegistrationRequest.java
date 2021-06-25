package com.kpi.springlabs.backend.model.dto.request;

import com.kpi.springlabs.backend.validation.constraints.ValidPassword;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@ApiModel(value = "Registration request",
        description = "The request body in JSON format is used to register a new user.")
public class RegistrationRequest {

    @Pattern(regexp = "^(?!.*\\.\\.)(?!.*\\.$)[^\\W][\\w.]{2,32}$", message = "Incorrect username format.")
    @ApiModelProperty(value = "Username", position = 1, example = "tester12345", required = true)
    private String username;

    @Email(message = "Incorrect email format.")
    @Size(min = 6, max = 32, message = "The email must be from 6 to 32 characters long.")
    @ApiModelProperty(value = "Email for registration", position = 2, example = "name@test.com", required = true)
    private String email;

    @ValidPassword
    @ApiModelProperty(value = "Password", position = 3, example = "Pass123@", required = true)
    private String password;
}
