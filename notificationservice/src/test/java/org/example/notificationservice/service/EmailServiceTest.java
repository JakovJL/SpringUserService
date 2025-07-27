package org.example.notificationservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class EmailServiceTest {
    @Autowired
    private EmailService emailService;

    @MockBean
    private JavaMailSender mailSender;

    @Test
    void sendEmail_ShouldCallMailSender() {
        String to = "MyTestMail@gmail.com";
        String subject = "Test";
        String text = "Test message";

        emailService.sendEmail(to, subject, text);

        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}