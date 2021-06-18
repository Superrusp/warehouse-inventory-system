package com.kpi.springlabs.backend.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("classpath:jwt.properties")
@ConfigurationProperties(prefix = "jwt.token")
public class JwtTokenProperties {

    private String secretKey;
    private long expiredTimeSecAccessToken;
    private long expiredTimeSecRefreshToken;
    private long expiredTimeSecConfirmationToken;
}
