package com.kpi.springlabs.backend.repository.mongo;

import com.kpi.springlabs.backend.model.RefreshToken;

import java.util.Date;
import java.util.Optional;

public interface RefreshTokenRepository {

    Optional<RefreshToken> findByToken(String token);

    void save(RefreshToken refreshToken);

    void delete(String id);

    void deleteByToken(String token);

    Long deleteByExpiryDateLessThan(Date date);
}
