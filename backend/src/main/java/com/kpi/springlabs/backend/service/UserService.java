package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.User;
import com.kpi.springlabs.backend.model.dto.request.ChangePasswordRequest;
import com.kpi.springlabs.backend.model.dto.request.RegistrationRequest;

public interface UserService {

    User createUser(RegistrationRequest registrationRequest);

    void updateUser(User user);

    void changePassword(String username, ChangePasswordRequest changePasswordRequest);

    void changePassword(User user, String newPassword);

    User getByEmail(String email);
}
