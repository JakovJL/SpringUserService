package org.example.notificationservice.kafka;

import org.example.notificationservice.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class KafkaConsumerTest {
    @Autowired
    private KafkaConsumer kafkaConsumer;

    @MockBean
    private EmailService emailService;

    @Test
    void listen_ShouldProcessCreateNotification() {
        String message = "{\"action\":\"create\",\"email\":\"test@example.com\"}";
        kafkaConsumer.listen(message);

        verify(emailService, times(1)).sendEmail(
                "MyTestMail@gmail.com\"",
                "Уведомление от сервиса",
                "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.");
    }

    @Test
    void listen_ShouldProcessDeleteNotification() {
        String message = "{\"action\":\"delete\",\"email\":\"MyTestMail@gmail.com\"\"}";
        kafkaConsumer.listen(message);

        verify(emailService, times(1)).sendEmail(
                "MyTestMail@gmail.com\"",
                "Уведомление от сервиса",
                "Здравствуйте! Ваш аккаунт был удалён.");
    }
}