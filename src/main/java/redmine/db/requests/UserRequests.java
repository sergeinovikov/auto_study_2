package redmine.db.requests;

import redmine.managers.Manager;
import redmine.model.project.Project;
import redmine.model.role.Role;
import redmine.model.user.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс описывающий создание, чтение, редактирование и удаление пользователей в БД
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
                            user.setLastLoginOn(
                                    map.get("last_login_on") != null
                                            ? ((Timestamp) map.get("last_login_on")).toLocalDateTime()
                                            : null
                            );
                            user.setLanguage(
                                    Language.of(
                                            (String) map.get("language")
                                    )
                            );
                            user.setAuthSourceId((Integer) map.get("auth_source_id"));
                            user.setCreatedOn(((Timestamp) map.get("created_on")).toLocalDateTime());
                            user.setUpdatedOn(((Timestamp) map.get("updated_on")).toLocalDateTime());
                            user.setType((String) map.get("type"));
                            user.setIdentityUrl((String) map.get("Identity_url"));
                            user.setMailNotification(
                                    MailNotification.of(
                                            (String) map.get("mail_notification")
                                    )
                            );
                            user.setSalt((String) map.get("salt"));
                            user.setMustChangePasswd((Boolean) map.get("must_change_passwd"));
                            user.setPasswdChangedOn(((Timestamp) map.get("passwd_changed_on")).toLocalDateTime());
                            return user;
                        }
                )
                .collect(Collectors.toList());
    }

    public static User getUserById(Integer id) {
        String query = "SELECT * FROM users WHERE id=?";
        return getUser(query, id);
    }

    public static User getUserByLogin(String login) {
        String query = "SELECT * FROM users WHERE login=?";
        return getUser(query, login);
    }

    private static User getUser(String query, Object... parameters) {
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query, parameters);

        User userFromDb = result.stream()
                .map(map -> {
                            User user = new User();
                            user.setId((Integer) map.get("id"));
                            user.setLogin((String) map.get("login"));
                            user.setHashedPassword((String) map.get("hashed_password"));
                            user.setFirstName((String) map.get("firstname"));
                            user.setLastName((String) map.get("lastname"));
                            user.setAdmin((Boolean) map.get("admin"));
                            user.setStatus((Integer) map.get("status"));
                            user.setLastLoginOn(
                                    map.get("last_login_on") != null
                                            ? ((Timestamp) map.get("last_login_on")).toLocalDateTime()
                                            : null
                            );
                            user.setLanguage(
                                    Language.of(
                                            (String) map.get("language")
                                    )
                            );
                            user.setAuthSourceId((Integer) map.get("auth_source_id"));
                            user.setCreatedOn(
                                    map.get("created_on") == null
                                            ? null
                                            : ((Timestamp) map.get("created_on")).toLocalDateTime()
                            );
                            user.setUpdatedOn(
                                    map.get("updated_on") == null
                                            ? null
                                            : ((Timestamp) map.get("updated_on")).toLocalDateTime()
                            );
                            user.setType((String) map.get("type"));
                            user.setIdentityUrl((String) map.get("Identity_url"));
                            user.setMailNotification(
                                    MailNotification.of(
                                            (String) map.get("mail_notification")
                                    )
                            );
                            user.setSalt((String) map.get("salt"));
                            user.setMustChangePasswd((Boolean) map.get("must_change_passwd"));
                            user.setPasswdChangedOn(
                                    map.get("passwd_changed_on") == null
                                            ? null
                                            : ((Timestamp) map.get("passwd_changed_on")).toLocalDateTime()
                            );
                            return user;
                        }
                )
                .findFirst()
                .orElse(null);

        if (userFromDb != null) {
            String emailQuery = "SELECT id, user_id, address, is_default, \"notify\", created_on, updated_on\n" +
                    "FROM public.email_addresses\n" +
                    "WHERE user_id=?;\n";
            List<Map<String, Object>> emailResult = Manager.dbConnection.executePreparedQuery(emailQuery,
                    userFromDb.getId()
            );

            List<EmailAddress> userEmail = emailResult.stream()
                    .map(map -> {
                                EmailAddress email = new EmailAddress();
                                email.setId((Integer) map.get("id"));
                                email.setUserId((Integer) map.get("user_id"));
                                email.setAddress((String) map.get("address"));
                                email.setIsDefault((Boolean) map.get("is_default"));
                                email.setNotify((Boolean) map.get("notify"));
                                email.setCreatedOn(((Timestamp) map.get("created_on")).toLocalDateTime());
                                email.setCreatedOn(((Timestamp) map.get("updated_on")).toLocalDateTime());
                                return email;
                            }
                    )
                    .collect(Collectors.toList());
            userFromDb.setEmail(
                    userEmail.size() == 0
                            ? null
                            : userEmail.get(0)
            );

            String apiKeyQuery = "SELECT id, user_id, \"action\", value, created_on, updated_on\n" +
                    "FROM public.tokens\n" +
                    "WHERE user_id=?;\n";
            List<Map<String, Object>> apiKeyResult = Manager.dbConnection.executePreparedQuery(apiKeyQuery,
                    userFromDb.getId()
            );

            if (!apiKeyResult.isEmpty()) {
                List<Token> userApiKey = apiKeyResult.stream()
                        .map(map -> {
                                    Token apiKey = new Token();
                                    apiKey.setId((Integer) map.get("id"));
                                    apiKey.setUserId((Integer) map.get("user_id"));
                                    apiKey.setAction(
                                            Action.of(
                                                    (String) map.get("action")
                                            )
                                    );
                                    apiKey.setValue((String) map.get("value"));
                                    apiKey.setCreatedOn(((Timestamp) map.get("created_on")).toLocalDateTime());
                                    apiKey.setUpdatedOn(
                                            map.get("updated_on") == null
                                                    ? null
                                                    : ((Timestamp) map.get("updated_on")).toLocalDateTime()
                                    );
                                    return apiKey;
                                }
                        )
                        .collect(Collectors.toList());
                userFromDb.setApiToken(
                        userApiKey.size() == 0
                                ? null
                                : userApiKey.get(0)
                );
            }
        }
        return userFromDb;
    }

    public static User addUser(User user) {
        String userQuery = "INSERT INTO public.users\n" +
                "(id, login, hashed_password, firstname, lastname, \"admin\", status, last_login_on, \"language\", auth_source_id, created_on, updated_on, \"type\", identity_url, mail_notification, salt, must_change_passwd, passwd_changed_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;\n";
        List<Map<String, Object>> userResult = Manager.dbConnection.executePreparedQuery(userQuery,
                user.getLogin(),
                user.getHashedPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getAdmin(),
                user.getStatus(),
                user.getLastLoginOn(),
                user.getLanguage().toString().toLowerCase(),
                user.getAuthSourceId(),
                user.getCreatedOn(),
                user.getUpdatedOn(),
                user.getType(),
                user.getIdentityUrl(),
                user.getMailNotification().toString().toLowerCase(),
                user.getSalt(),
                user.getMustChangePasswd(),
                user.getPasswdChangedOn()
        );
        user.setId((Integer) userResult.get(0).get("id"));

        String emailQuery = "INSERT INTO public.email_addresses\n" +
                "(id, user_id, address, is_default, \"notify\", created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?) RETURNING id;\n";
        List<Map<String, Object>> emailResult = Manager.dbConnection.executePreparedQuery(emailQuery,
                user.getId(),
                user.getEmail().getAddress(),
                user.getEmail().getIsDefault(),
                user.getEmail().getNotify(),
                user.getEmail().getCreatedOn(),
                user.getEmail().getUpdatedOn()
        );
        user.getEmail().setUserId(user.getId());
        user.getEmail().setId((Integer) emailResult.get(0).get("id"));

        String apiQuery = "INSERT INTO public.tokens\n" +
                "(id, user_id, \"action\", value, created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?)  RETURNING id;\n";
        List<Map<String, Object>> apiKeyResult = Manager.dbConnection.executePreparedQuery(apiQuery,
                user.getId(),
                user.getApiToken().getAction().toString().toLowerCase(),
                user.getApiToken().getValue(),
                user.getApiToken().getCreatedOn(),
                user.getApiToken().getUpdatedOn()
        );
        user.getApiToken().setUserId(user.getId());
        user.getApiToken().setId((Integer) apiKeyResult.get(0).get("id"));
        return user;
    }

    public static User updateById(User user) {
        String userQuery = "UPDATE public.users\n" +
                "SET login=?, hashed_password=?, firstname=?, lastname=?, \"admin\"=?, status=?, last_login_on=?, \"language\"=?, auth_source_id=?, created_on=?, updated_on=?, \"type\"=?, identity_url=?, mail_notification=?, salt=?, must_change_passwd=?, passwd_changed_on=?\n" +
                "WHERE id=? RETURNING id\n";
        List<Map<String, Object>> userResult = Manager.dbConnection.executePreparedQuery(userQuery,
                user.getLogin(),
                user.getHashedPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getAdmin(),
                user.getStatus(),
                user.getLastLoginOn(),
                user.getLanguage().toString().toLowerCase(),
                user.getAuthSourceId(),
                user.getCreatedOn(),
                user.getUpdatedOn(),
                user.getType(),
                user.getIdentityUrl(),
                user.getMailNotification().toString().toLowerCase(),
                user.getSalt(),
                user.getMustChangePasswd(),
                user.getPasswdChangedOn(),
                user.getId()
        );
        user.setId((Integer) userResult.get(0).get("id"));
        return updateUserLinkedTables(user);
    }

    public static User updateByLogin(User user) {
        String userQuery = "UPDATE public.users\n" +
                "SET hashed_password=?, firstname=?, lastname=?, \"admin\"=?, status=?, last_login_on=?, \"language\"=?, auth_source_id=?, created_on=?, updated_on=?, \"type\"=?, identity_url=?, mail_notification=?, salt=?, must_change_passwd=?, passwd_changed_on=?\n" +
                "WHERE login=? RETURNING id\n";
        List<Map<String, Object>> userResult = Manager.dbConnection.executePreparedQuery(userQuery,
                user.getHashedPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getAdmin(),
                user.getStatus(),
                user.getLastLoginOn(),
                user.getLanguage().toString().toLowerCase(),
                user.getAuthSourceId(),
                user.getCreatedOn(),
                user.getUpdatedOn(),
                user.getType(),
                user.getIdentityUrl(),
                user.getMailNotification().toString().toLowerCase(),
                user.getSalt(),
                user.getMustChangePasswd(),
                user.getPasswdChangedOn(),
                user.getLogin()
        );
        user.setId((Integer) userResult.get(0).get("id"));
        return updateUserLinkedTables(user);
    }

    private static User updateUserLinkedTables(User user) {
        String emailQuery = "UPDATE public.email_addresses\n" +
                "SET address=?, is_default=?, \"notify\"=?, created_on=?, updated_on=?\n" +
                "WHERE user_id=? RETURNING id;\n";
        List<Map<String, Object>> emailResult = Manager.dbConnection.executePreparedQuery(emailQuery,
                user.getEmail().getAddress(),
                user.getEmail().getIsDefault(),
                user.getEmail().getNotify(),
                user.getEmail().getCreatedOn(),
                user.getEmail().getUpdatedOn(),
                user.getId()
        );
        user.getEmail().setUserId(user.getId());
        user.getEmail().setId(
                emailResult.size() == 0
                        ? null
                        : (Integer) emailResult.get(0).get("id")
        );

        String apiQuery = "UPDATE public.tokens\n" +
                "SET \"action\"=?, value=?, created_on=?, updated_on=?\n" +
                "WHERE user_id=? RETURNING id;\n";
        List<Map<String, Object>> apiKeyResult = Manager.dbConnection.executePreparedQuery(apiQuery,
                user.getApiToken().getAction().toString().toLowerCase(),
                user.getApiToken().getValue(),
                user.getApiToken().getCreatedOn(),
                user.getApiToken().getUpdatedOn(),
                user.getId()
        );
        user.getApiToken().setUserId(user.getId());
        user.getApiToken().setId((Integer) apiKeyResult.get(0).get("id"));

        return user;
    }

    public static void deleteUser(User user) {
        String userQuery = "DELETE FROM public.users\n" +
                "WHERE id=?;\n";
        Manager.dbConnection.executeDeleteQuery(userQuery,
                user.getId()
        );

        String emailQuery = "DELETE FROM public.email_addresses\n" +
                "WHERE id=?;\n";
        Manager.dbConnection.executeDeleteQuery(emailQuery,
                user.getEmail().getId()
        );

        String apiQuery = "DELETE FROM public.tokens\n" +
                "WHERE id=?;\n";
        Manager.dbConnection.executeDeleteQuery(apiQuery,
                user.getApiToken().getId()
        );

    }

    public static void addUserWithRole(User user, Project project, Role role) {
        String membersQuery = "INSERT INTO public.members\n" +
                "(id, user_id, project_id, created_on, mail_notification)\n" +
                "VALUES(DEFAULT, ?, ?, ?, DEFAULT) RETURNING id;\n";
        List<Map<String, Object>> addMembersConnection = Manager.dbConnection.executePreparedQuery(membersQuery,
                user.getId(),
                project.getId(),
                LocalDateTime.now()
        );

        Integer membersId = (Integer) addMembersConnection.get(0).get("id");

        String membersRoleQuery = "INSERT INTO public.member_roles\n" +
                "(id, member_id, role_id, inherited_from)\n" +
                "VALUES(DEFAULT, ?, ?, DEFAULT) RETURNING id;\n";
        Manager.dbConnection.executePreparedQuery(membersRoleQuery,
                membersId,
                role.getId()
        );

    }
}
