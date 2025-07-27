package org.example.springuserservice.service;

import org.example.springuserservice.dto.UserDTO;
import java.util.List;

/**
 * Сервис для работы с пользователями
 * Предоставляет CRUD-операции для пользователя
 */
public interface UserService {

    /**
     * Создает нового пользователя на основе переданных данных
     *
     * @param dto DTO-объект с данными для создания пользователя
     * @return DTO-объект созданного пользователя
     */
    UserDTO create(UserDTO dto);

    /**
     * Получает пользователя по его идентификатору
     *
     * @param id идентификатор пользователя
     * @return DTO-объект найденного пользователя
     */
    UserDTO get(Long id);

    /**
     * Получает список всех пользователей
     *
     * @return список DTO-объектов пользователей
     */
    List<UserDTO> getAll();

    /**
     * Обновляет данные пользователя
     *
     * @param id идентификатор пользователя для обновления
     * @param dto DTO-объект с новыми данными пользователя
     * @return DTO-объект обновленного пользователя
     */
    UserDTO update(Long id, UserDTO dto);

    /**
     * Удаляет пользователя по его идентификатору
     *
     * @param id идентификатор пользователя для удаления
     */
    void delete(Long id);
}