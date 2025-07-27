package org.example.notificationservice.dto;

public class NotificationMessage {

    /**
     * Тип действия с пользователем (create/delete)
     */
    private String action;

    /**
     * Email пользователя, для которого предназначено уведомление
     */
    private String email;

    /**
     * Конструктор с параметрами для создания объекта уведомления
     *
     * @param action тип действия (create/delete)
     * @param email email пользователя
     */
    public NotificationMessage(String action, String email) {
        this.action = action;
        this.email = email;
    }

    /**
     * Конструктор по умолчанию, необходим для десериализации JSON
     */
    public NotificationMessage() {
    }

    /**
     * Получает тип действия
     *
     * @return тип действия (create/delete)
     */
    public String getAction() {
        return action;
    }

    /**
     * Устанавливает тип действия
     *
     * @param action тип действия (create/delete)
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Получает email пользователя
     *
     * @return email пользователя
     */
    public String getEmail() {
        return email;
    }

    /**
     * Устанавливает email пользователя
     *
     * @param email email пользователя
     */
    public void setEmail(String email) {
        this.email = email;
    }
}