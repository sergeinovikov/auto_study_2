package redmine.model.dto;

import lombok.Data;

/**
 * Класс передачи данных пользователя между REST-ответом и Java-приложением
 */

@Data
public class UserDto {
    private UserInfo user;
}
