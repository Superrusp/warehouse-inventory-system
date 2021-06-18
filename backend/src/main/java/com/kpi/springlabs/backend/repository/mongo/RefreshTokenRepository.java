package com.kpi.springlabs.backend.repository.mongo;

import com.kpi.springlabs.backend.model.RefreshToken;
import com.kpi.springlabs.backend.model.User;

import java.util.Optional;

public interface RefreshTokenRepository {

    Optional<RefreshToken> findByToken(String token);

    void save(RefreshToken refreshToken);

    void delete(String id);

    void deleteByUser(User user);
}
