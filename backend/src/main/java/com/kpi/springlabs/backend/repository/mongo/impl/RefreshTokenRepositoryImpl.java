package com.kpi.springlabs.backend.repository.mongo.impl;

import com.kpi.springlabs.backend.model.RefreshToken;
import com.kpi.springlabs.backend.repository.mongo.RefreshTokenRepository;
import com.kpi.springlabs.backend.utils.Constants;
import com.mongodb.client.result.DeleteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
@Slf4j
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public RefreshTokenRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        LOG.debug("Call query to find refresh token");
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.TokenFields.TOKEN_VALUE).is(token));
        RefreshToken refreshToken = mongoTemplate.findOne(query, RefreshToken.class);
        return refreshToken != null ? Optional.of(refreshToken) : Optional.empty();
    }

    @Override
    public void save(RefreshToken refreshToken) {
        LOG.debug("Call query to save refresh token");
        mongoTemplate.save(refreshToken);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Call query to delete refresh token");
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.BasicFields.ID).is(id));
        mongoTemplate.remove(query, RefreshToken.class);
    }

    @Override
    public void deleteByToken(String token) {
        LOG.debug("Call query to delete refresh token by its value");
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.TokenFields.TOKEN_VALUE).is(token));
        mongoTemplate.remove(query, RefreshToken.class);
    }

    @Override
    public Long deleteByExpiryDateLessThan(Date date) {
        LOG.debug("Call query to delete expired tokens");
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.TokenFields.EXPIRATION_DATE).lt(date));
        DeleteResult result = mongoTemplate.remove(query, RefreshToken.class);
        return result.getDeletedCount();
    }
}
