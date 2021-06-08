package com.kpi.springlabs.backend.model.dto.request;

import com.kpi.springlabs.backend.validation.constraints.FieldMatch;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
@ApiModel(value = "Password Reset Request",
        description = "The request body in JSON format is used to set a new password.")
public class PasswordResetRequest {

    @NotBlank(message = "The password cannot be blank.")
    @Size(min = 5, max = 50, message = "The password must be from 5 to 50 characters long.")
    @ApiModelProperty(value = "Password", position = 1, required = true)
    private String password;

    @NotBlank(message = "The password cannot be blank.")
    @Size(min = 5, max = 50, message = "The password must be from 5 to 50 characters long.")
    @ApiModelProperty(value = "Confirm Password", position = 2, required = true)
    private String confirmPassword;

    @NotBlank(message = "The token cannot be blank.")
    @ApiModelProperty(value = "Reset Password Token", position = 3, required = true)
    private String token;
}
