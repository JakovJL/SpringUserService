package org.example.notificationservice.controller;

import org.example.notificationservice.service.EmailSender;
import org.example.notificationservice.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final EmailSender emailSender; // Используем интерфейс

    public NotificationController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @PostMapping("/send")
    public String sendNotification(
            @RequestParam String email,
            @RequestParam String action) {

        String subject = "Уведомление от сервиса";
        String text = action.equals("create")
                ? "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан."
                : "Здравствуйте! Ваш аккаунт был удалён.";

        emailSender.sendEmail(email, subject, text);
        return "Notification sent to " + email;
    }
}