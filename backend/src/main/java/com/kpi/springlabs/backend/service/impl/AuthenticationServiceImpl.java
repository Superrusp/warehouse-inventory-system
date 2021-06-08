package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.enums.TokenType;
import com.kpi.springlabs.backend.exception.BadRequestException;
import com.kpi.springlabs.backend.model.ConfirmationToken;
import com.kpi.springlabs.backend.model.User;
import com.kpi.springlabs.backend.model.dto.request.AuthenticationRequest;
import com.kpi.springlabs.backend.model.dto.request.PasswordResetRequest;
import com.kpi.springlabs.backend.model.dto.request.RegistrationRequest;
import com.kpi.springlabs.backend.model.dto.response.AuthenticationResponse;
import com.kpi.springlabs.backend.security.token.JwtTokenProvider;
import com.kpi.springlabs.backend.security.user.SecurityUser;
import com.kpi.springlabs.backend.service.AuthenticationService;
import com.kpi.springlabs.backend.service.ConfirmationTokenService;
import com.kpi.springlabs.backend.service.EmailService;
import com.kpi.springlabs.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final EmailService emailService;
    private final ConfirmationTokenService confirmationTokenService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(UserService userService, EmailService emailService,
                                     ConfirmationTokenService confirmationTokenService, JwtTokenProvider jwtTokenProvider,
                                     AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.emailService = emailService;
        this.confirmationTokenService = confirmationTokenService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void register(RegistrationRequest registrationRequest) {
        LOG.debug("Register user by request: {}", registrationRequest);
        User user = userService.createUser(registrationRequest);
        String username = user.getUsername();
        LOG.debug("User '{}' was created", username);
        String emailToken = jwtTokenProvider.generateMailConfirmationToken(username);
        ConfirmationToken confirmationToken = new ConfirmationToken(emailToken, user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        emailService.sendEmailToConfirmRegistration(user, emailToken);
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        LOG.debug("Authenticate user by request: {}", authenticationRequest);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken((SecurityUser) authentication.getPrincipal());
        LOG.debug("User was authenticated");
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public void confirmEmail(String token) {
        LOG.debug("Confirm email");
        ConfirmationToken confirmationToken = confirmationTokenService.getConfirmationToken(token);
        boolean isTokenValid = jwtTokenProvider.validateToken(TokenType.MAIL_CONFIRMATION_TOKEN.name(), confirmationToken.getToken());
        if (!isTokenValid) {
            throw new BadRequestException("JWT token is invalid");
        }
        final User user = confirmationToken.getUser();
        user.setEnabled(true);
        userService.updateUser(user);
        LOG.debug("User was activated");
        confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
        LOG.debug("Confirmation token was deleted");
    }

    @Override
    public void recoverPassword(String email) {
        User user = userService.getByEmail(email);
        LOG.debug("User: {}", user);
        String resetPasswordToken = jwtTokenProvider.generateMailConfirmationToken(user.getUsername());
        ConfirmationToken confirmationToken = new ConfirmationToken(resetPasswordToken, user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        emailService.sendEmailToConfirmPasswordReset(user, resetPasswordToken);
    }

    @Override
    public void resetPassword(PasswordResetRequest passwordResetRequest) {
        LOG.debug("Reset password");
        ConfirmationToken confirmationToken = confirmationTokenService.getConfirmationToken(passwordResetRequest.getToken());
        boolean isTokenValid = jwtTokenProvider.validateToken(TokenType.MAIL_CONFIRMATION_TOKEN.name(), confirmationToken.getToken());
        if (!isTokenValid) {
            throw new BadRequestException("JWT token is invalid");
        }
        final User user = confirmationToken.getUser();
        userService.changePassword(user, passwordResetRequest.getPassword());
        confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
        LOG.debug("Confirmation token was deleted");
    }
}
