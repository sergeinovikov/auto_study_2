package redmine.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Класс передачи данных пользователя между REST-ответом и Java-приложением
 */

@Data
@Accessors(chain = true)
public class UserDto {
    private UserInfo user;
}
