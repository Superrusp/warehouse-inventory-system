package com.kpi.springlabs.backend.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("classpath:mail.properties")
@ConfigurationProperties(prefix = "mail")
public class MailProperties {

    private String sourceUrl;
    private String registrationSubject;
    private String registrationText;
    private String registrationEndPointUri;
    private String passwordResetSubject;
    private String passwordResetText;
    private String passwordResetEndPointUri;
    private String emailSender;
}
