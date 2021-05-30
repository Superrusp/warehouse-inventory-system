package com.kpi.springlabs.backend.repository.mongo;

import com.kpi.springlabs.backend.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> save(User user);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
