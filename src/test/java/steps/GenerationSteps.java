package steps;

import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Пусть;
import redmine.cucumber.ParametersValidator;
import redmine.managers.Context;
import redmine.model.project.Project;
import redmine.model.role.*;
import redmine.model.user.User;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

public class GenerationSteps {

    @Пусть("Существует пользователь {string}")
    public void userGeneration (String userStashId, Map<String, String> parameters) {
        User user = new User();
        ParametersValidator.validateUserParameters(parameters);

        if (parameters.containsKey("Администратор")) {
            user.setAdmin(parseBoolean(parameters.get("Администратор")));
        }
        if (parameters.containsKey("Статус")) {
            user.setStatus(parseInt(parameters.get("Статус")));
        }
        user.generate();
        Context.put(userStashId, user);
    }

    @И("В системе существует роль {string} с параметрами:")
    public void generateRoleWithParameters(String roleStashId, Map<String, String> parameters) {
        Role role = new Role();
        ParametersValidator.validateRoleParameters(parameters);

        if (parameters.containsKey("Позиция")) {
            role.setPosition(parseInt(parameters.get("Позиция")));
        }
        if (parameters.containsKey("Встроенная")) {
            role.setBuiltin(parseInt(parameters.get("Встроенная")));
        }
        if (parameters.containsKey("Задача может быть назначена этой роли")) {
            role.setAssignable(parseBoolean(parameters.get("Задача может быть назначена этой роли")));
        }
        if (parameters.containsKey("Видимость задач")) {
            role.setIssuesVisibility(
                    IssuesVisibility.of(parameters.get("Видимость задач"))
            );
        }
        if (parameters.containsKey("Видимость пользователей")) {
            role.setUsersVisibility(
                    UsersVisibility.of(parameters.get("Видимость пользователей"))
            );
        }
        if (parameters.containsKey("Видимость трудозатрат")) {
            role.setTimeEntriesVisibility(
                    TimeEntriesVisibility.of(parameters.get("Видимость трудозатрат"))
            );
        }
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
}
