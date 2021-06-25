package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.dto.request.AuthenticationRequest;
import com.kpi.springlabs.backend.model.dto.request.PasswordResetRequest;
import com.kpi.springlabs.backend.model.dto.request.RegistrationRequest;
import com.kpi.springlabs.backend.model.dto.response.AuthenticationResponse;

public interface AuthenticationService {

    void register(RegistrationRequest registrationRequest);

    AuthenticationResponse login(AuthenticationRequest authenticationRequest);

    void confirmEmail(String token);

    void recoverPassword(String email);

    void resetPassword(PasswordResetRequest passwordResetRequest);

    AuthenticationResponse refreshTokens(String refreshTokenValue);
}
