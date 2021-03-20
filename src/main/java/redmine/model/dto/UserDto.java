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
        return "UserDto{" +
                "id=" + user.getId() +
                ", login='" + user.getLogin() + '\'' +
                ", admin=" + user.getAdmin() +
                ", firstname='" + user.getFirstname() + '\'' +
                ", lastname='" + user.getLastname() + '\'' +
                ", mail='" + user.getMail() + '\'' +
                ", created_on=" + user.getCreated_on() +
                ", last_login_on=" + user.getLast_login_on() +
                ", api_key='" + user.getApi_key() + '\'' +
                ", status=" + user.getStatus() +
                ", password='" + user.getPassword() + '\'' +
                '}';
    }
}

