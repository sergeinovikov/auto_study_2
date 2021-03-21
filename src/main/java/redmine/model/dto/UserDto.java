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

    @Override
    public String toString() {
        return "UserDto{" + System.lineSeparator() +
                "id=" + user.getId() + System.lineSeparator() +
                "login='" + user.getLogin() + '\'' + System.lineSeparator() +
                "admin=" + user.getAdmin() + System.lineSeparator() +
                "firstname='" + user.getFirstname() + '\'' + System.lineSeparator() +
                "lastname='" + user.getLastname() + '\'' + System.lineSeparator() +
                "mail='" + user.getMail() + '\'' + System.lineSeparator() +
                "created_on=" + user.getCreated_on() + System.lineSeparator() +
                "last_login_on=" + user.getLast_login_on() + System.lineSeparator() +
                "api_key='" + user.getApi_key() + '\'' + System.lineSeparator() +
                "status=" + user.getStatus() + System.lineSeparator() +
                "password='" + user.getPassword() + '\'' + System.lineSeparator() +
                '}';
    }
}

