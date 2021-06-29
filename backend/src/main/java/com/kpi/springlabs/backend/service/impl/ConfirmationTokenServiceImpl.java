package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.exception.ObjectNotFoundException;
import com.kpi.springlabs.backend.model.ConfirmationToken;
import com.kpi.springlabs.backend.repository.mongo.ConfirmationTokenRepository;
import com.kpi.springlabs.backend.service.ConfirmationTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public ConfirmationToken getConfirmationToken(String token) {
        LOG.debug("Get confirmation token");
        return confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> {
                    LOG.error("ConfirmationToken not found");
                    throw new ObjectNotFoundException("ConfirmationToken not found");
                });
    }

    @Override
    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        LOG.debug("Save confirmation token");
        confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public void deleteConfirmationToken(String id) {
        LOG.debug("Delete ConfirmationToken(id = {})", id);
        confirmationTokenRepository.delete(id);
    }

    @Override
    public Long deleteAllExpiredSince(Date date) {
        LOG.debug("Delete expired confirmation tokens");
        return confirmationTokenRepository.deleteByExpiryDateLessThan(date);
    }
}
