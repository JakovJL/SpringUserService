package org.example.springuserservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.hateoas.RepresentationModel;

/**
 * DTO используется для передачи данных между слоями приложения
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO extends RepresentationModel<UserDTO> {
    /**
     * Уникальный идентификатор пользователя
     * Генерируется автоматически при создании
     */
    private Long id;

    /**
     * Имя пользователя
     * Не может быть null или пустым
     */
    private String name;

    /**
     * Электронная почта пользователя
     * Должна быть уникальной для каждого пользователя
     * Не может быть null или пустой
     */
    private String email;

    /**
     * Возраст пользователя
     * Может быть null, если возраст не указан
     * Должен быть положительным числом, если указан
     */
    private Integer age;
}