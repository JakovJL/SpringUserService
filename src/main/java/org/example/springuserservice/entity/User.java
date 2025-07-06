package org.example.springuserservice.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    /**
     * Уникальный идентификатор пользователя
     * Генерируется автоматически при сохранении в базу данных
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя
     * Не может быть null
     */
    @Column(nullable = false)
    private String name;

    /**
     * Электронная почта пользователя
     * Должна быть уникальной для каждого пользователя
     * Не может быть null
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Возраст пользователя
     * Может быть null, если возраст не указан
     * Должен быть положительным числом, если указан
     */
    private Integer age;

    /**
     * Дата и время создания пользователя
     * Устанавливается автоматически при создании
     * Не может быть изменена после создания
     * Не может быть null
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Обновляет данные пользователя
     *
     * @param name новое имя пользователя
     * @param email новая электронная почта
     * @param age новый возраст
     */
    public void updateDetails(String name, String email, Integer age) {
        if (name != null) {
            this.name = name;
        }
        if (email != null) {
            this.email = email;
        }
        if (age != null) {
            this.age = age;
        }
    }
}