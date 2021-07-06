package com.example.demo.service;

import com.example.demo.domain.SimpleMailMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSender {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    public void send(String mailTo, String subject, String message) {
        SimpleMailMessageBuilder mailMessageBuilder = new SimpleMailMessageBuilder();

        mailSender.send(
                mailMessageBuilder
                        .setFrom(username)
                        .setTo(mailTo)
                        .setSubject(subject)
                        .setText(message)
                        .build());
    }
}
