package redmine.db.requests;

import redmine.managers.Manager;
import redmine.model.role.Role;
import redmine.model.user.Language;
import redmine.model.user.MailNotification;
import redmine.model.user.User;
import redmine.utils.DateFormatter;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс описывающий создание, чтение, редактирование и обновление пользователей в БД
 */

public class UserRequests {

    public static List<User> getAllUsers() {
        String query = "SELECT * FROM users";
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        return result.stream()
                .map(map -> {
                            User user = new User();
                            user.setId((Integer) map.get("id"));
                            user.setLogin((String) map.get("login"));
                            user.setHashedPassword((String) map.get("hashed_password"));
                            user.setFirstName((String) map.get("firstname"));
                            user.setLastName((String) map.get("lastname"));
                            user.setAdmin((Boolean) map.get("admin"));
                            user.setStatus((Integer) map.get("status"));
                            user.setLastLoginOn((Date) map.get("last_login_on"));
                            user.setLanguage(
                                    Language.of(
                                            (String) map.get("language")
                                    )
                            );
                            user.setAuthSourceId((String) map.get("auth_source_id"));
                            user.setCreatedOn((Date) map.get("created_on"));
                            user.setUpdatedOn((Date) map.get("updated_on"));
                            user.setType((String) map.get("type"));
                            user.setIdentityUrl((String) map.get("Identity_url"));
                            user.setMailNotification(
                                    MailNotification.of(
                                            (String) map.get("mail_notification")
                                    )
                            );
                            user.setSalt((String) map.get("salt"));
                            user.setMustChangePasswd((Boolean) map.get("must_change_passwd"));
                            user.setPasswdChangedOn((Date) map.get("passwd_changed_on"));
                            return user;
                        }
                )
                .collect(Collectors.toList());
    }

    public static User getUser(User objectUser) {
        return getAllUsers().stream()
                .filter(user -> {
                            if (objectUser.getId() == null) {
                                return objectUser.getLogin().equals(user.getLogin());
                            } else {
                                return objectUser.getId().equals(user.getId());
                            }
                        }
                )
                .findFirst()
                .orElse(null);
    }

    public static User addUser(User user) {
        String query = "INSERT INTO public.users\n" +
                "(id, login, hashed_password, firstname, lastname, \"admin\", status, last_login_on, \"language\", auth_source_id, created_on, updated_on, \"type\", identity_url, mail_notification, salt, must_change_passwd, passwd_changed_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;\n";
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query,
                user.getLogin(),
                user.getHashedPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getAdmin(),
                user.getStatus(),
                user.getLastLoginOn(), //TODO
                user.getLanguage().toString().toLowerCase(),
                user.getAuthSourceId(),
                DateFormatter.convertDate(user.getCreatedOn()),
                DateFormatter.convertDate(user.getUpdatedOn()),
                user.getType(),
                user.getIdentityUrl(),
                user.getMailNotification().toString().toLowerCase(),
                user.getSalt(),
                user.getMustChangePasswd(),
                DateFormatter.convertDate(user.getPasswdChangedOn())
        );
        user.setId((Integer) result.get(0).get("id"));
        return user;
    }

    public static User updateUser(User user) {
        String query = "UPDATE public.users\n" +
                "SET hashed_password=?, firstname=?, lastname=?, \"admin\"=?, status=?, last_login_on=?, \"language\"=?, auth_source_id=?, created_on=?, updated_on=?, \"type\"=?, identity_url=?, mail_notification=?, salt=?, must_change_passwd=?, passwd_changed_on=?\n" +
                "WHERE login=? RETURNING id\n";
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query,
                user.getHashedPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getAdmin(),
                user.getStatus(),
                user.getLastLoginOn(), //TODO
                user.getLanguage().toString().toLowerCase(),
                user.getAuthSourceId(),
                DateFormatter.convertDate(user.getCreatedOn()),
                DateFormatter.convertDate(user.getUpdatedOn()),
                user.getType(),
                user.getIdentityUrl(),
                user.getMailNotification().toString().toLowerCase(),
                user.getSalt(),
                user.getMustChangePasswd(),
                DateFormatter.convertDate(user.getPasswdChangedOn()),
                user.getLogin()

        );
        user.setId((Integer) result.get(0).get("id"));
        return user;
    }

    public static void deleteUser(User user) {
        String query = "DELETE FROM public.users\n" +
                "WHERE id=?;\n";
        Manager.dbConnection.executeDeleteQuery(query,
                user.getId()
        );
    }
}
