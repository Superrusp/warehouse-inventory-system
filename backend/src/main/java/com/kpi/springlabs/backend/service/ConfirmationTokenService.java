package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.ConfirmationToken;

import java.util.Date;

public interface ConfirmationTokenService {

    ConfirmationToken getConfirmationToken(String token);

    void saveConfirmationToken(ConfirmationToken confirmationToken);

    void deleteConfirmationToken(String id);

    Long deleteAllExpiredSince(Date date);
}
