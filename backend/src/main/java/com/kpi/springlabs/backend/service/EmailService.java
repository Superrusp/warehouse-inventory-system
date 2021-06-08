package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.User;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendEmail(final SimpleMailMessage mailMessage);

    void sendEmailToConfirmRegistration(User user, String token);

    void sendEmailToConfirmPasswordReset(User user, String token);

    SimpleMailMessage constructEmail(String recipient, String subject, String body);
}
