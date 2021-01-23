package redmine.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс-модель для пользователя, использумый в классе передчи данных пользователя
 */

@Data
public class UserInfo {
    private Integer id;
    private String login;
    private Boolean admin;
    private String firstname;
    private String lastname;
    private String mail;
    private LocalDateTime created_on;
    private LocalDateTime last_login_on;
    private String api_key;
    private Integer status;
    private String password;
}
