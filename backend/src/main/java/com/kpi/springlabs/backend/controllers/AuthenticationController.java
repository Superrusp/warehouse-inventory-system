package com.kpi.springlabs.backend.controllers;

import com.kpi.springlabs.backend.model.dto.request.AuthenticationRequest;
import com.kpi.springlabs.backend.model.dto.request.RegistrationRequest;
import com.kpi.springlabs.backend.model.dto.response.AuthenticationResponse;
import com.kpi.springlabs.backend.service.AuthenticationService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
@Api(value = "Authentication Controller", tags = "authentication")
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @ApiOperation(value = "New user registration", response = AuthenticationResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The user registered successfully"),
            @ApiResponse(code = 409, message = "Username already taken or Email Address already in use")
    })
    @PostMapping("/registration")
    public AuthenticationResponse registration(@ApiParam(value = "Registration Request")
                                               @Valid @RequestBody RegistrationRequest registrationRequest) {
        LOG.debug("Request for user registration");
        return authenticationService.register(registrationRequest);
    }

    @ApiOperation(value = "User authentication", response = AuthenticationResponse.class)
    @ApiResponse(code = 200, message = "The token was generated successfully")
    @PostMapping("/login")
    public AuthenticationResponse login(@ApiParam(value = "Authentication Request")
                                        @Valid @RequestBody AuthenticationRequest authenticationRequest) {
        LOG.debug("Request for user login");
        return authenticationService.login(authenticationRequest);
    }
}
