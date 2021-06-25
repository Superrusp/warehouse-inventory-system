package com.kpi.springlabs.backend.repository.mongo;

import com.kpi.springlabs.backend.model.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenRepository {

    Optional<ConfirmationToken> findByToken(String token);

    void save(ConfirmationToken confirmationToken);

    void delete(String id);
}
