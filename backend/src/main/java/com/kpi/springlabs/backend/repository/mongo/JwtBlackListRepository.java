package com.kpi.springlabs.backend.repository.mongo;

import com.kpi.springlabs.backend.model.JwtBlackList;

import java.util.Date;
import java.util.Optional;

public interface JwtBlackListRepository {

    Optional<JwtBlackList> findByToken(String token);

    Optional<JwtBlackList> save(JwtBlackList jwtBlackList);

    Long deleteByExpiryDateLessThan(Date date);
}
