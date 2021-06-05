package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.config.properties.MailProperties;
import com.kpi.springlabs.backend.model.User;
import com.kpi.springlabs.backend.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final MailProperties mailProperties;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender, MailProperties mailProperties) {
        this.emailSender = emailSender;
        this.mailProperties = mailProperties;
    }

    @Override
    public void sendEmail(SimpleMailMessage mailMessage) {
        LOG.debug("Send message {} to {}", mailMessage, mailMessage.getTo());
        emailSender.send(mailMessage);
    }

    @Override
    public void sendEmailWithConfirmationLink(User user, String token) {
        LOG.debug("Sending email to confirm registration");
        final String recipientAddress = user.getEmail();
        final String subject = mailProperties.getRegistrationSubject();
        final String message = mailProperties.getRegistrationText();
        final String confirmationUrl = String.format("%s%s?code=%s",
                mailProperties.getSourceUrl(),
                mailProperties.getRegistrationEndPointUri(),
                token);
        sendEmail(constructEmail(recipientAddress, subject, message + ": " + confirmationUrl));
    }

    @Override
    public SimpleMailMessage constructEmail(String recipient, String subject, String body) {
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        mailMessage.setTo(recipient);
        mailMessage.setFrom(mailProperties.getEmailSender());
        return mailMessage;
    }
}
