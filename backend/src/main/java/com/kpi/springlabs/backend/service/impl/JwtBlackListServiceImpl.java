package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.exception.ConflictException;
import com.kpi.springlabs.backend.model.JwtBlackList;
import com.kpi.springlabs.backend.repository.mongo.JwtBlackListRepository;
import com.kpi.springlabs.backend.service.JwtBlackListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class JwtBlackListServiceImpl implements JwtBlackListService {

    private final JwtBlackListRepository jwtBlackListRepository;

    @Autowired
    public JwtBlackListServiceImpl(JwtBlackListRepository jwtBlackListRepository) {
        this.jwtBlackListRepository = jwtBlackListRepository;
    }

    @Override
    public JwtBlackList getJwtTokenFromBlackList(String token) {
        LOG.debug("Get jwt token from black list");
        return jwtBlackListRepository.findByToken(token)
                .orElse(null);
    }

    @Override
    public JwtBlackList saveJwtTokenToBlackList(JwtBlackList jwtBlackList) {
        LOG.debug("Save jwt token to black list");
        return jwtBlackListRepository.save(jwtBlackList)
                .orElseThrow(() -> {
                    LOG.error("Jwt token cannot be added to the black list");
                    return new ConflictException("Jwt token cannot be added to the black list");
                });
    }

    @Override
    public Long deleteAllExpiredSince(Date date) {
        LOG.debug("Delete expired blacklisted tokens");
        return jwtBlackListRepository.deleteByExpiryDateLessThan(date);
    }
}
