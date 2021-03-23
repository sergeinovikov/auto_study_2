package steps;

import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Пусть;
import redmine.api.implementations.RestApiClient;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.Response;
import redmine.cucumber.ParametersValidator;
import redmine.db.requests.UserRequests;
import redmine.managers.Context;
import redmine.model.dto.UserDto;
import redmine.model.project.Project;
import redmine.model.role.*;
import redmine.model.user.EmailAddress;
import redmine.model.user.Language;
import redmine.model.user.User;
import redmine.ui.pages.NewUserPage;
import redmine.utils.StringGenerators;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static redmine.ui.pages.helpers.Pages.getPage;

public class GenerationSteps {

    @Пусть("Существует пользователь {string}")
    public void userGeneration (String userStashId, Map<String, String> parameters) {

        ParametersValidator.validateUserParameters(parameters);
        User user = new User();

        if (parameters.containsKey("Администратор")) {
            user.setAdmin(parseBoolean(parameters.get("Администратор")));
        }
        if (parameters.containsKey("Статус")) {
            user.setStatus(parseInt(parameters.get("Статус")));
        }
        if (parameters.containsKey("Язык")) {
            Language language = Language.getByDescription(parameters.get("Язык"));
            user.setLanguage(language);
        }

        user.generate();
        Context.put(userStashId, user);
    }

    @И("В системе существует роль {string} с параметрами:")
    public void generateRoleWithParameters(String roleStashId, Map<String, String> parameters) {
        Role role = new Role();
        ParametersValidator.validateRoleParameters(parameters);

        if (parameters.containsKey("Права")) {
            RolePermissions permissions = Context.get(parameters.get("Права"), RolePermissions.class);
            role.setPermissions(permissions);
        }

        role.generate();
        Context.put(roleStashId, role);
    }


    @И("В системе существует проект {string} с параметрами:")
    public void generateProjectWithParameters(String projectStashId, Map<String, String> parameters) {
        Project project = new Project();
        ParametersValidator.validateProjectParameters(parameters);

        if (parameters.containsKey("Публичный")) {
            project.setIsPublic(parseBoolean(parameters.get("Публичный")));
        }
        if (parameters.containsKey("Статус")) {
            project.setStatus(parseInt(parameters.get("Статус")));
        }

        project.generate();
        Context.put(projectStashId, project);
    }

    @И("Существует список прав роли {string} с правами:")
    public void putPermissionsToContext(String permissionsStashId, List<String> permissionDescriptions) {
        Set<Permissions> permissions = permissionDescriptions.stream()
                .map(Permissions::of)
                .collect(Collectors.toSet());
        RolePermissions rolePermissions = new RolePermissions(permissions);
        Context.put(permissionsStashId, rolePermissions);
    }

    @И("У пользователя {string} с ролью {string} существует доступ к проекту {string}")
    public void addUserRoleProjectConnection(String userStashId, String roleStashId, String projectStashId) {
        User user = Context.get(userStashId, User.class);
        Role role = Context.get(roleStashId, Role.class);
        Project project = Context.get(projectStashId, Project.class);

        UserRequests.addUserWithRole(user, project, role);
    }

    @И("Заполнить данные нового пользователя {string} случаныйми корректными значениями")
    public void createNewUser(String newUserStashId) {
        String login = "SN" + StringGenerators.randomEnglishLowerString(8);
        String firstName = "Ser" + StringGenerators.randomEnglishString(8);
        String lastName = "Nov" + StringGenerators.randomEnglishString(8);
        String mail = StringGenerators.randomEmail(8);

        User userBeforeCreation = new User()
                .setLogin(login)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(
                        new EmailAddress().setAddress(mail)
                );

        Context.put(newUserStashId, userBeforeCreation);

        getPage(NewUserPage.class).fillInUserData(userBeforeCreation);
    }

    @И("Создан API-клиент {string} с API-ключом пользователя {string}")
    public void createApiClient(String apiClientStashId, String userStashId) {
        User user = Context.get(userStashId, User.class);
        ApiClient apiClient = new RestApiClient(user);
        Context.put(apiClientStashId, apiClient);
    }

    @И("Изменить у пользователя {string} изменить почтовый адрес на невалидный, а пароль - на строку из 4 символов и назначить данные пользователю {string}")
    public void editUserDtoMailAndPswd(String userDtoStashId, String invalidUserDtoStashId) {
        UserDto userDto = Context.get(userDtoStashId, UserDto.class);

        userDto.getUser()
                .setMail("invalid_email")
                .setPassword("1234");

        Context.put(invalidUserDtoStashId, userDto);
    }

    @И("У пользователя {string} из ответа {string} изменить статус на {int}")
    public void editUserDtoStatus(String userDtoStashId, String responseStashId, int statusCode) {
        Response response = Context.get(responseStashId, Response.class);
        UserDto userDto = response.getBody(UserDto.class);

        userDto.getUser().setStatus(statusCode);

        Context.put(userDtoStashId, userDto);
    }

}
