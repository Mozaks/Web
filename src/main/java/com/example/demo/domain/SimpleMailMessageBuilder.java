package com.example.demo.domain;

import org.springframework.mail.SimpleMailMessage;

public class SimpleMailMessageBuilder {
    SimpleMailMessage simpleMailMessage;

    public SimpleMailMessageBuilder() {
        this.simpleMailMessage = new SimpleMailMessage();
    }

    public SimpleMailMessageBuilder setFrom(String from) {
        this.simpleMailMessage.setFrom(from);
        return this;
    }

    public SimpleMailMessageBuilder setTo(String to) {
        this.simpleMailMessage.setTo(to);
        return this;
    }

    public SimpleMailMessageBuilder setSubject(String subject) {
        this.simpleMailMessage.setSubject(subject);
        return this;
    }

    public SimpleMailMessageBuilder setText(String text) {
        this.simpleMailMessage.setText(text);
        return this;
    }

    public SimpleMailMessage build() {
        return new SimpleMailMessage(this.simpleMailMessage);
    }
}
