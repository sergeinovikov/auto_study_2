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
    private Integer id;
    @CucumberName("логин")
    private String login = "SN" + StringGenerators.randomEnglishLowerString(8);
    private String firstName = "Serg" + StringGenerators.randomEnglishLowerString(8);
    private String lastName = "Nov" + StringGenerators.randomEnglishLowerString(8);
    private EmailAddress email = new EmailAddress();
    private Language language = Language.RU;
    private Boolean admin = false;
    public String password = StringGenerators.randomPassword(8);
    private String salt = StringGenerators.generateHexString(32);
    private String hashedPassword = StringGenerators.generateHashPassword(this.salt, this.password);
    private Boolean mustChangePasswd = false;
    private Integer status = 2;

    private MailNotification mailNotification = MailNotification.values()[new Random().nextInt(MailNotification.values().length)];

    private Token apiToken = new Token();
    private LocalDateTime lastLoginOn = null;
    private LocalDateTime createdOn = LocalDateTime.now();
    private LocalDateTime updatedOn = LocalDateTime.now();
    private LocalDateTime passwdChangedOn = LocalDateTime.now();
    private Integer authSourceId = null;
    private String type = "User";
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
}
