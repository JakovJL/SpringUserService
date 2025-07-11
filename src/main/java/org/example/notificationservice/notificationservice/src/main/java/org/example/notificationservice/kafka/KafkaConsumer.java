package org.example.notificationservice.kafka;

import org.example.notificationservice.dto.NotificationMessage;
import org.example.notificationservice.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private final EmailService emailService;

    /**
     * Конструктор с внедрением зависимостей.
     *
     * @param emailService  сервис отправки email
     */
    public KafkaConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Обрабатывает сообщения из топика Kafka.
     *
     * @param message JSON-строка с данными уведомления
     */
    @KafkaListener(topics = "user-notifications", groupId = "notification-group")
    public void listen(String message) {
        try {
            NotificationMessage notification = parseMessage(message);

            String subject = "Уведомление от сервиса";
            String text;
            if (notification.getAction().equals("create")) {
                text = "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.";
            } else {
                text = "Здравствуйте! Ваш аккаунт был удалён.";
            }

            emailService.sendEmail(notification.getEmail(), subject, text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private NotificationMessage parseMessage(String message) {
        try {
            String[] parts = message.replaceAll("[{}\"]", "").split(",");
            String action = null;
            String email = null;

            for (String part : parts) {
                String[] keyValue = part.split(":");
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();

                    if (key.equals("action")) {
                        action = value;
                    } else if (key.equals("email")) {
                        email = value;
                    }
                }
            }

            if (action == null || email == null) {
                throw new IllegalArgumentException("Invalid message format");
            }

            return new NotificationMessage(action, email);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse notification message", e);
        }
    }
}