package com.kpi.springlabs.backend.repository.mongo.impl;

import com.kpi.springlabs.backend.model.ConfirmationToken;
import com.kpi.springlabs.backend.repository.mongo.ConfirmationTokenRepository;
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
public class ConfirmationTokenRepositoryImpl implements ConfirmationTokenRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ConfirmationTokenRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<ConfirmationToken> findByToken(String token) {
        LOG.debug("Call query to find confirmation token");
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.TokenFields.TOKEN_VALUE).is(token));
        ConfirmationToken confirmationToken = mongoTemplate.findOne(query, ConfirmationToken.class);
        return confirmationToken != null ? Optional.of(confirmationToken) : Optional.empty();
    }

    @Override
    public void save(ConfirmationToken confirmationToken) {
        LOG.debug("Call query to save confirmation token");
        mongoTemplate.save(confirmationToken);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Call query to delete confirmation token");
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.BasicFields.ID).is(id));
        mongoTemplate.remove(query, ConfirmationToken.class);
    }

    @Override
    public Long deleteByExpiryDateLessThan(Date date) {
        LOG.debug("Call query to delete expired tokens");
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.TokenFields.EXPIRATION_DATE).lt(date));
        DeleteResult result = mongoTemplate.remove(query, ConfirmationToken.class);
        return result.getDeletedCount();
    }
}
