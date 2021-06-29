package com.kpi.springlabs.backend.repository.mongo.impl;

import com.kpi.springlabs.backend.model.JwtBlackList;
import com.kpi.springlabs.backend.repository.mongo.JwtBlackListRepository;
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
public class JwtBlackListRepositoryImpl implements JwtBlackListRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public JwtBlackListRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<JwtBlackList> findByToken(String token) {
        LOG.debug("Call query to find jwt token in the black list");
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.TokenFields.TOKEN).is(token));
        JwtBlackList jwtInBlackList = mongoTemplate.findOne(query, JwtBlackList.class);
        return jwtInBlackList != null ? Optional.of(jwtInBlackList) : Optional.empty();
    }

    @Override
    public Optional<JwtBlackList> save(JwtBlackList jwtBlackList) {
        LOG.debug("Call query to save jwt token in the black list");
        JwtBlackList jwtInBlackList = mongoTemplate.insert(jwtBlackList);
        return Optional.of(jwtInBlackList);
    }

    @Override
    public Long deleteByExpiryDateLessThan(Date date) {
        LOG.debug("Call query to delete expired tokens");
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.TokenFields.EXPIRATION_DATE).lt(date));
        DeleteResult result = mongoTemplate.remove(query, JwtBlackList.class);
        return result.getDeletedCount();
    }
}
