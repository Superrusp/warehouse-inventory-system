package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.exception.ObjectNotFoundException;
import com.kpi.springlabs.backend.model.Role;
import com.kpi.springlabs.backend.repository.mongo.RoleRepository;
import com.kpi.springlabs.backend.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRole(String name) {
        LOG.debug("Getting Role(name = {})", name);
        return roleRepository.findByName(name)
                .orElseThrow(() -> {
                    LOG.error("Role '{}' not found", name);
                    return new ObjectNotFoundException(String.format("Role '%s' not found", name));
                });
    }
}
