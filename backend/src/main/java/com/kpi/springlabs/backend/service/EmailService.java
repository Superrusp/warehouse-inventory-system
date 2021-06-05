package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.User;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendEmail(final SimpleMailMessage mailMessage);

    void sendEmailWithConfirmationLink(User user, String token);

    SimpleMailMessage constructEmail(String recipient, String subject, String body);
}
