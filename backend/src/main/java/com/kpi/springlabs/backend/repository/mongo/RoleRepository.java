package com.kpi.springlabs.backend.repository.mongo;

import com.kpi.springlabs.backend.model.Role;

import java.util.Optional;

public interface RoleRepository {

    Optional<Role> findByName(String name);
}
