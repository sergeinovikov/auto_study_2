package redmine.model.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import redmine.db.requests.UserRequests;
import redmine.model.Generatable;
import redmine.utils.CryptoGenerator;
import redmine.utils.StringGenerators;
import java.util.Date;
import java.util.Random;

/**
 * Класс-модель пользователя в системе
 * Описание методов создачния, чтения, редактирования и удаления
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
public class User implements Generatable<User> {
    private Integer id;
    private String login = StringGenerators.randomEnglishLowerString(8);
    private String firstName = "Serg" + StringGenerators.randomEnglishLowerString(8);
    private String lastName = "Nov" + StringGenerators.randomEnglishLowerString(8);
    private EmailAddress email = new EmailAddress();
    private Language language = Language.values()[new Random().nextInt(Language.values().length)];
    private Boolean admin = false;
    private String salt = CryptoGenerator.generateHEX(32);
    public String password = StringGenerators.randomString(8, StringGenerators.ENGLISH + StringGenerators.DIGITS + StringGenerators.CHARACTERS);
    private String hashedPassword = CryptoGenerator.generatePassword(this.salt, this.password);
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
        User user = UserRequests.getUser(this);
        if (user == null)
            return null;
        return user;
    }

    @Override
    public User update() {
        return UserRequests.updateUser(this);
    }

    @Override
    public User create() {
        return UserRequests.addUser(this);
    }

    @Override
    public void delete() {
        User user = UserRequests.getUser(this);
        if (user.id == null) {
            new IllegalArgumentException("Пользователь с данным Id не найден");
        } else {
            UserRequests.deleteUser(this);
        }
    }
}
