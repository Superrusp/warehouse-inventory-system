package com.kpi.springlabs.backend.repository.mongo.impl;

import com.kpi.springlabs.backend.model.User;
import com.kpi.springlabs.backend.repository.mongo.UserRepository;
import com.kpi.springlabs.backend.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<User> save(User user) {
        LOG.debug("Save user");
        mongoTemplate.save(user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        LOG.debug("Call query to find User(username = {})", username);
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.UserFields.USERNAME).is(username));
        User user = mongoTemplate.findOne(query, User.class);
        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        LOG.debug("Call query to find User(email = {})", email);
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.UserFields.EMAIL).is(email));
        User user = mongoTemplate.findOne(query, User.class);
        return user != null ? Optional.of(user) : Optional.empty();
    }
}
