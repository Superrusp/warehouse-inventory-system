package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.RefreshToken;

import java.util.Date;

public interface RefreshTokenService {

    RefreshToken getRefreshToken(String token);

    void save(RefreshToken refreshToken);

    void delete(String id);

    void deleteByTokenValue(String tokenValue);

    Long deleteAllExpiredSince(Date date);
}
