package com.physio.backend.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(String messageBody) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo("sripavithra1711@gmail.com");
            message.setSubject("New Clinic Inquiry");
            message.setText(messageBody);

            mailSender.send(message);

            System.out.println("Email sent successfully!");
        } catch (Exception e) {
            System.err.println("EMAIL FAILED:");
            e.printStackTrace();
            throw e; // ensures Spring still returns 500
        }
    }
}