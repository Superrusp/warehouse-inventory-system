package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.dto.request.AuthenticationRequest;
import com.kpi.springlabs.backend.model.dto.request.RegistrationRequest;
import com.kpi.springlabs.backend.model.dto.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse register(RegistrationRequest registrationRequest);

    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
}
