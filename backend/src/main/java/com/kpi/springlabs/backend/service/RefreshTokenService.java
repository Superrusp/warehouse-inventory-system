package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.RefreshToken;
import com.kpi.springlabs.backend.model.User;

public interface RefreshTokenService {

    RefreshToken getRefreshToken(String token);

    void save(RefreshToken refreshToken);

    void delete(String id);

    void deleteByUserIfExists(User user);
}
