package com.kpi.springlabs.backend.controllers;

import com.kpi.springlabs.backend.model.dto.request.ChangePasswordRequest;
import com.kpi.springlabs.backend.service.UserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(value = "User Controller", tags = "users")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Change user password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Password updated successfully"),
            @ApiResponse(code = 400, message = "Invalid password"),
            @ApiResponse(code = 404, message = "Username not found")
    })
    @PatchMapping("/{username}/change-password")
    public ResponseEntity<String> changePassword(@ApiParam(value = "Username", required = true) @PathVariable String username,
                                                 @ApiParam("Change Password Request") @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        LOG.debug("Request for user's password change");
        userService.changePassword(username, changePasswordRequest);
        return ResponseEntity.ok().body("The password was successfully changed.");
    }
}
