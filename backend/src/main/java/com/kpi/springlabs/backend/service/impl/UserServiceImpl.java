package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.enums.RoleEnum;
import com.kpi.springlabs.backend.exception.ConflictException;
import com.kpi.springlabs.backend.model.Role;
import com.kpi.springlabs.backend.model.User;
import com.kpi.springlabs.backend.model.dto.request.RegistrationRequest;
import com.kpi.springlabs.backend.repository.mongo.UserRepository;
import com.kpi.springlabs.backend.service.RoleService;
import com.kpi.springlabs.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(RegistrationRequest registrationRequest) {
        LOG.debug("Creating user by request: {}", registrationRequest);
        String username = registrationRequest.getUsername();
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    LOG.error("User with username {} already exists", username);
                    throw new ConflictException(String.format("Username '%s' already exists", user.getUsername()));
                });

        String email = registrationRequest.getEmail();
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    LOG.debug("User with email {} already exists", email);
                    throw new ConflictException(String.format("Email '%s' already exists", user.getEmail()));
                });

        Role role = roleService.getRole(RoleEnum.USER.name());
        Set<Role> roles = Set.of(role);
        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .roles(roles)
                .build();

        userRepository.save(user);
        return user;
    }
}
