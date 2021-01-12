package redmine.model.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import redmine.db.requests.RoleRequests;
import redmine.db.requests.UserRequests;
import redmine.model.Generatable;
import redmine.model.role.Role;
import redmine.utils.PasswordGenerator;
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
public class User implements Generatable<User> {
    private Integer id;
    private String login = StringGenerators.randomEnglishLowerString(8);
    private String firstName = "Serg" + StringGenerators.randomEnglishLowerString(8);
    private String lastName = "Nov" + StringGenerators.randomEnglishLowerString(8);
    private String email = StringGenerators.randomEmail();
    private Language language = Language.values()[new Random().nextInt(Language.values().length)];
    private Boolean admin = false;
    private String salt = PasswordGenerator.generateSalt();
    private String hashedPassword = PasswordGenerator.generatePassword(this.salt);
    private Boolean mustChangePasswd = false;
    private Integer status = new Random().nextInt(3) + 1;

    private MailNotification mailNotification = MailNotification.values()[new Random().nextInt(MailNotification.values().length)];

    private Date lastLoginOn = null;
    private Date createdOn = new Date();
    private Date updatedOn = new Date();
    private Date passwdChangedOn = new Date();
    private String authSourceId = null;
    private String type = "User";
    private String identityUrl = null;



    @Override
    public User read() {
        User user = UserRequests.getUser(this);
        if (user == null)
            return null;
        return this;
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
