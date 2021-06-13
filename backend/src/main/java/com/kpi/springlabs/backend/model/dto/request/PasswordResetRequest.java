package com.kpi.springlabs.backend.model.dto.request;

import com.kpi.springlabs.backend.validation.constraints.FieldMatch;
import com.kpi.springlabs.backend.validation.constraints.ValidPassword;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
@ApiModel(value = "Password Reset Request",
        description = "The request body in JSON format is used to set a new password.")
public class PasswordResetRequest {

    @ValidPassword
    @ApiModelProperty(value = "Password", position = 1, required = true)
    private String password;

    @ValidPassword
    @ApiModelProperty(value = "Confirm Password", position = 2, required = true)
    private String confirmPassword;

    @NotBlank(message = "The token cannot be blank.")
    @ApiModelProperty(value = "Reset Password Token", position = 3, required = true)
    private String token;
}
