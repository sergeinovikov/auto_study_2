package redmine.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import redmine.model.user.User;

/**
 * Класс передачи данных пользователя между REST-ответом и Java-приложением
 */

@Data
@Accessors(chain = true)
public class UserDto {
    private UserInfo user;

    public static User readUserDtoById(Integer id) {
        return new User()
                .setId(id)
                .read();
    }

    public static User readUserDtoByLogin(String login) {
        return new User()
                .setLogin(login)
                .read();
    }
}

