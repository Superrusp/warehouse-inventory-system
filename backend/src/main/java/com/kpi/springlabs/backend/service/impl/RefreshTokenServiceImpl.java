package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.exception.ObjectNotFoundException;
import com.kpi.springlabs.backend.model.RefreshToken;
import com.kpi.springlabs.backend.repository.mongo.RefreshTokenRepository;
import com.kpi.springlabs.backend.service.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public RefreshToken getRefreshToken(String token) {
        LOG.debug("Get refresh token");
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> {
                    LOG.error("Refresh token not found");
                    throw new ObjectNotFoundException("Refresh token not found");
                });
    }

    @Override
    public void save(RefreshToken refreshToken) {
        LOG.debug("Save refresh token");
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Delete refresh token");
        refreshTokenRepository.delete(id);
    }

    @Override
    public void deleteByTokenValue(String tokenValue) {
        LOG.debug("Delete refresh token by its value");
        refreshTokenRepository.deleteByToken(tokenValue);
    }

    @Override
    public Long deleteAllExpiredSince(Date date) {
        LOG.debug("Delete expired refresh tokens");
        return refreshTokenRepository.deleteByExpiryDateLessThan(date);
    }
}
