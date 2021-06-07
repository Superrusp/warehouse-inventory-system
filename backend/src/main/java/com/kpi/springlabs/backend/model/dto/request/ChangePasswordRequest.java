package com.kpi.springlabs.backend.model.dto.request;

import com.kpi.springlabs.backend.validation.constraints.FieldMatch;
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
    @Size(min = 5, max = 50, message = "The password must be from 5 to 50 characters long.")
    @ApiModelProperty(value = "Old Password", position = 1, required = true)
    private String oldPassword;

    @NotBlank(message = "The password cannot be blank.")
    @Size(min = 5, max = 50, message = "The password must be from 5 to 50 characters long.")
    @ApiModelProperty(value = "New Password", position = 2, required = true)
    private String newPassword;

    @NotBlank(message = "The password cannot be blank.")
    @Size(min = 5, max = 50, message = "The password must be from 5 to 50 characters long.")
    @ApiModelProperty(value = "Confirm New Password", position = 3, required = true)
    private String confirmNewPassword;
}
