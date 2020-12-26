package redmine.model.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import redmine.model.Generatable;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class User implements Generatable<User> {
    private Integer id;
    private String login;
    private String firstName;
    private String lastNName;
    private String email;
    private Language language;
    private Boolean admin;
    private String hashedPassword;
    private Boolean mustChangePasswd;
    private String status;

    private MailNotification mailNotification;

    private Date lastLoginOn;
    private Date createdOn;
    private Date updatedOn;
    private Date passwd_changedOn;
    private String authSourceId;
    private String type;
    private String identity_url;
    private String salt;


    @Override
    public User read() {
        //TODO
        return null;
    }

    @Override
    public User update() {
        //TODO
        return null;
    }

    @Override
    public User create() {
        //TODO
        return null;
    }
}
