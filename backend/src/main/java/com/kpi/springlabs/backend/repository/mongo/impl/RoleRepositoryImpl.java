package com.kpi.springlabs.backend.repository.mongo.impl;

import com.kpi.springlabs.backend.model.Role;
import com.kpi.springlabs.backend.repository.mongo.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class RoleRepositoryImpl implements RoleRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public RoleRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<Role> findByName(String name) {
        LOG.debug("Call query to find role by name");
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        Role role = mongoTemplate.findOne(query, Role.class);
        return role != null ? Optional.of(role) : Optional.empty();
    }
}
