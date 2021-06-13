package com.kpi.springlabs.backend.model.dto.request;

import com.kpi.springlabs.backend.validation.constraints.FieldMatch;
import com.kpi.springlabs.backend.validation.constraints.ValidPassword;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@FieldMatch(first = "newPassword", second = "confirmNewPassword", message = "The password fields must match")
@ApiModel(value = "Change Password Request",
        description = "The request body in JSON format is used to change user's password")
public class ChangePasswordRequest {

    @NotBlank(message = "The password cannot be blank.")
    @Size(min = 8, max = 16, message = "The password must be from 8 to 16 characters long.")
    @ApiModelProperty(value = "Old Password", position = 1, required = true)
    private String oldPassword;

    @ValidPassword
    @ApiModelProperty(value = "New Password", position = 2, required = true)
    private String newPassword;

    @ValidPassword
    @ApiModelProperty(value = "Confirm New Password", position = 3, required = true)
    private String confirmNewPassword;
}
