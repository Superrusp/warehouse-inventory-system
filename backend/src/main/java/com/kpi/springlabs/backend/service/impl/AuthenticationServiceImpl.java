package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.model.User;
import com.kpi.springlabs.backend.model.dto.request.AuthenticationRequest;
import com.kpi.springlabs.backend.model.dto.request.RegistrationRequest;
import com.kpi.springlabs.backend.model.dto.response.AuthenticationResponse;
import com.kpi.springlabs.backend.security.token.JwtTokenProvider;
import com.kpi.springlabs.backend.security.user.CustomUserDetailsService;
import com.kpi.springlabs.backend.security.user.SecurityUser;
import com.kpi.springlabs.backend.service.AuthenticationService;
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
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(UserService userService, CustomUserDetailsService userDetailsService,
                                     JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationResponse register(RegistrationRequest registrationRequest) {
        LOG.debug("Register user by request: {}", registrationRequest);
        User user = userService.createUser(registrationRequest);
        String username = user.getUsername();
        LOG.debug("User '{}' was created", username);
        SecurityUser securityUser = userDetailsService.loadUserByUsername(username);
        String token = jwtTokenProvider.generateToken(securityUser);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
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
}
