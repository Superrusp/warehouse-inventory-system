package com.kpi.springlabs.backend.controllers;

import com.kpi.springlabs.backend.model.dto.request.AuthenticationRequest;
import com.kpi.springlabs.backend.model.dto.request.RegistrationRequest;
import com.kpi.springlabs.backend.model.dto.response.AuthenticationResponse;
import com.kpi.springlabs.backend.service.AuthenticationService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "New user registration", notes = "Performs registration of the user's account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The user registered successfully"),
            @ApiResponse(code = 409, message = "Username already taken or Email Address already in use")
    })
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@ApiParam(value = "Registration Request")
                                               @Valid @RequestBody RegistrationRequest registrationRequest) {
        LOG.debug("Request for user registration");
        authenticationService.register(registrationRequest);
        return ResponseEntity.ok("Account was successfully registered. Please check your inbox for a confirmation email.");
    }

    @ApiOperation(value = "User authentication", notes = "Performs login of the user", response = AuthenticationResponse.class)
    @ApiResponse(code = 200, message = "The token was generated successfully")
    @PostMapping("/login")
    public AuthenticationResponse login(@ApiParam(value = "Authentication Request")
                                        @Valid @RequestBody AuthenticationRequest authenticationRequest) {
        LOG.debug("Request for user login");
        return authenticationService.login(authenticationRequest);
    }

    @ApiOperation(value = "Email confirmation", notes = "Performs confirmation of the user's email")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "The token expired or has incorrect subject"),
            @ApiResponse(code = 404, message = "The token not found")
    })
    @GetMapping("/email/confirm")
    public ResponseEntity<String> confirmEmail(@ApiParam(value = "Token", required = true)
                                               @RequestParam(name = "code") final String token) {
        LOG.debug("Request for email confirmation");
        authenticationService.confirmEmail(token);
        return ResponseEntity.ok("Email confirmed successfully.");
    }
}
