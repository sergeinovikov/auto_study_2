package redmine.model.user;

import lombok.Data;
import lombok.experimental.Accessors;
import redmine.db.requests.UserRequests;
import redmine.model.Generatable;
import redmine.utils.StringGenerators;

import java.util.Date;
import java.util.Random;

/**
 * Класс-модель пользователя в системе
 * Описание методов создачния, чтения, редактирования и удаления
 */

@Data
@Accessors(chain = true)
public class User implements Generatable<User> {
    private Integer id;
    private String login = "SN" + StringGenerators.randomEnglishLowerString(8);
    private String firstName = "Serg" + StringGenerators.randomEnglishLowerString(8);
    private String lastName = "Nov" + StringGenerators.randomEnglishLowerString(8);
    private EmailAddress email = new EmailAddress();
    private Language language = Language.values()[new Random().nextInt(Language.values().length)];
    private Boolean admin = false;
    private String salt = StringGenerators.generateHexString(32);
    public String password = StringGenerators.randomPassword(8);
    private String hashedPassword = StringGenerators.generateHashPassword(this.salt, this.password);
    private Boolean mustChangePasswd = false;
    private Integer status = 2;

    private MailNotification mailNotification = MailNotification.values()[new Random().nextInt(MailNotification.values().length)];

    private Token apiToken = new Token();
    private Date lastLoginOn = null;
    private Date createdOn = new Date();
    private Date updatedOn = new Date();
    private Date passwdChangedOn = new Date();
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
