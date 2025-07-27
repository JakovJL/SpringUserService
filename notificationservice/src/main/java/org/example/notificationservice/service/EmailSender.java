package org.example.notificationservice.service;

public interface EmailSender {
    /**
     * Отправляет email-уведомление
     *
     * @param to адрес получателя
     * @param subject тема письма
     * @param text текст сообщения
     */
    void sendEmail(String to, String subject, String text);
}