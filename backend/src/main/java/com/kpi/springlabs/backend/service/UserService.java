package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.User;
import com.kpi.springlabs.backend.model.dto.request.RegistrationRequest;

public interface UserService {

    User createUser(RegistrationRequest registrationRequest);

    void updateUser(User user);
}
