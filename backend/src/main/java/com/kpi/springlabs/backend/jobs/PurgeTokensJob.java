package com.kpi.springlabs.backend.jobs;

import com.kpi.springlabs.backend.service.ConfirmationTokenService;
import com.kpi.springlabs.backend.service.JwtBlackListService;
import com.kpi.springlabs.backend.service.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@PropertySource("classpath:scheduler.properties")
@Slf4j
public class PurgeTokensJob {

    private final ConfirmationTokenService confirmationTokenService;
    private final RefreshTokenService refreshTokenService;
    private final JwtBlackListService jwtBlackListService;

    @Autowired
    public PurgeTokensJob(ConfirmationTokenService confirmationTokenService, RefreshTokenService refreshTokenService,
                          JwtBlackListService jwtBlackListService) {
        this.confirmationTokenService = confirmationTokenService;
        this.refreshTokenService = refreshTokenService;
        this.jwtBlackListService = jwtBlackListService;
    }

    @Scheduled(cron = "${purge.cron.expression.confirmation-tokens}", zone = "${timezone}")
    public void purgeMailConfirmationTokens() {
        LOG.debug("Run Job: purge confirmation tokens");
        Date now = Date.from(Instant.now());
        LOG.debug("Running job for confirmation tokens older than: {}", now);
        Long tokensCount = confirmationTokenService.deleteAllExpiredSince(now);
        LOG.debug("Number of confirmation tokens deleted: {}", tokensCount);
    }

    @Scheduled(cron = "${purge.cron.expression.refresh-tokens}", zone = "${timezone}")
    public void purgeRefreshTokens() {
        LOG.debug("Run Job: purge refresh tokens");
        Date now = Date.from(Instant.now());
        LOG.debug("Running job for refresh tokens older than: {}", now);
        Long tokensCount = refreshTokenService.deleteAllExpiredSince(now);
        LOG.debug("Number of refresh tokens deleted: {}", tokensCount);
    }

    @Scheduled(cron = "${purge.cron.expression.blacklisted-tokens}", zone = "${timezone}")
    public void purgeBlacklistedTokens() {
        LOG.debug("Run Job: purge blacklisted tokens");
        Date now = Date.from(Instant.now());
        LOG.debug("Running job for blacklisted tokens older than: {}", now);
        Long tokensCount = jwtBlackListService.deleteAllExpiredSince(now);
        LOG.debug("Number of blacklisted tokens deleted: {}", tokensCount);
    }
}
