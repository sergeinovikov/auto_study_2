package redmine.model.user;

import lombok.Data;
import lombok.experimental.Accessors;
import redmine.db.requests.UserRequests;
import redmine.model.Generatable;
import redmine.ui.pages.helpers.CucumberName;
import redmine.utils.StringGenerators;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Класс-модель пользователя в системе
 * Описание методов создачния, чтения, редактирования и удаления
 */

@Data
@Accessors(chain = true)
@CucumberName("Пользователь")
public class User implements Generatable<User> {
    @CucumberName("Id")
    private Integer id;
    @CucumberName("Логин")
    private String login = "SN" + StringGenerators.randomEnglishLowerString(8);
    @CucumberName("Имя")
    private String firstName = "Serg" + StringGenerators.randomEnglishLowerString(8);
    @CucumberName("Фамилия")
    private String lastName = "Nov" + StringGenerators.randomEnglishLowerString(8);
    @CucumberName("Почта")
    private EmailAddress email = new EmailAddress();
    @CucumberName("Язык")
    private Language language = Language.RU;
    @CucumberName("Права админа")
    private Boolean admin = false;
    @CucumberName("Пароль")
    public String password = StringGenerators.randomPassword(8);
    @CucumberName("Соль")
    private String salt = StringGenerators.generateHexString(32);
    @CucumberName("Хэш-пароль")
    private String hashedPassword = StringGenerators.generateHashPassword(this.salt, this.password);
    @CucumberName("Смена пароля")
    private Boolean mustChangePasswd = false;
    @CucumberName("Статус")
    private Integer status = 2;

    @CucumberName("Уведомления")
    private MailNotification mailNotification = MailNotification.values()[new Random().nextInt(MailNotification.values().length)];

    @CucumberName("API-токен")
    private Token apiToken = new Token();
    @CucumberName("Дата последнего логина")
    private LocalDateTime lastLoginOn = null;
    @CucumberName("Дата создания")
    private LocalDateTime createdOn = LocalDateTime.now();
    @CucumberName("Дата обновления")
    private LocalDateTime updatedOn = LocalDateTime.now();
    @CucumberName("Дата смены пароля")
    private LocalDateTime passwdChangedOn = LocalDateTime.now();
    @CucumberName("Id ресурса авторизации")
    private Integer authSourceId = null;
    @CucumberName("Тип")
    private String type = "User";
    @CucumberName("URL идентификатор")
    private String identityUrl = null;


    @Override
    public User read() {
        return this.id == null
                ? UserRequests.getUserByLogin(this.login)
                : UserRequests.getUserById(this.id);
    }

    @Override
    public User update() {
        return this.id == null
                ? UserRequests.updateByLogin(this)
                : UserRequests.updateById(this);
    }

    @Override
    public User create() {
        return UserRequests.addUser(this);
    }

    @Override
    public void delete() {
        if (this.read() != null) {
            UserRequests.deleteUser(this);
        } else {
            throw new IllegalArgumentException("Пользователь с данным Id не найден");
        }
    }

    @Override
    public String toString() {
        return "User{" + System.lineSeparator() +
                "id=" + id + System.lineSeparator() +
                "login='" + login + '\'' + System.lineSeparator() +
                "firstName='" + firstName + '\'' + System.lineSeparator() +
                "lastName='" + lastName + '\'' + System.lineSeparator() +
                "email=" + email + System.lineSeparator() +
                "language=" + language + System.lineSeparator() +
                "admin=" + admin + System.lineSeparator() +
                "password='" + password + '\'' + System.lineSeparator() +
                "salt='" + salt + '\'' + System.lineSeparator() +
                "hashedPassword='" + hashedPassword + '\'' + System.lineSeparator() +
                "mustChangePasswd=" + mustChangePasswd + System.lineSeparator() +
                "status=" + status + System.lineSeparator() +
                "mailNotification=" + mailNotification + System.lineSeparator() +
                "apiToken=" + apiToken + System.lineSeparator() +
                "lastLoginOn=" + lastLoginOn + System.lineSeparator() +
                "createdOn=" + createdOn + System.lineSeparator() +
                "updatedOn=" + updatedOn + System.lineSeparator() +
                "passwdChangedOn=" + passwdChangedOn + System.lineSeparator() +
                "authSourceId=" + authSourceId + System.lineSeparator() +
                "type='" + type + '\'' + System.lineSeparator() +
                "identityUrl='" + identityUrl + '\'' + System.lineSeparator() +
                '}';
    }
}
