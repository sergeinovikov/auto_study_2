package ui_tests;

import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.db.requests.UserRequests;
import redmine.managers.Manager;
import redmine.model.project.Project;
import redmine.model.role.Permissions;
import redmine.model.role.Role;
import redmine.model.role.RolePermissions;
import redmine.model.user.User;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;
import redmine.ui.pages.ProjectsPage;
import redmine.utils.BrowserUtils;

import static redmine.ui.pages.Pages.getPage;

public class UiTestCase5 {
    private User user;
    private Project publicProject1;
    private Project privateProject2;
    private Project privateProject3;
    private Role role;

    @BeforeMethod(description = "Генерация пользователя, публичного проекта 1, приватного проекта 2 и приватного проекта 3 в Redmine. Пользователь подтверждён администратором (активен) и имеет доступ только к приватному проекту 3 с ролью из предусловия")
    public void prepareFixtures() {
        user = new User()
                .setStatus(1)
                .generate();
        role = new Role().
                setPermissions(new RolePermissions(Permissions.CLOSE_PROJECT))
                .generate();

        publicProject1 = new Project()
                .setStatus(1)
                .setIsPublic(true)
                .generate();

        privateProject2 = new Project()
                .setStatus(1)
                .setIsPublic(false)
                .generate();

        privateProject3 = new Project()
                .setStatus(1)
                .setIsPublic(false)
                .generate();

        UserRequests.addProjectAndRoleRelations(user, privateProject3, role);
    }

    @Test(description = "Кейс 5. Авторизация подтверждённым пользователем. Проверка видимости приватного проекта 3 с ролью, которая имеет доступ только к этому проекту")
    public void adminPrivateProjectCheck() {
        adminlogin();
        goToProjectPage();
    }

    @Step("Авторизация пользователем с правами администратора и ролью из предусловия")
    private void adminlogin() {
        Manager.openPage("login");

        getPage(LoginPage.class)
                .login(user.getLogin(), user.getPassword());

        Assert.assertTrue(BrowserUtils.isElementPresent(getPage(HeaderPage.class).getHomePage()));
    }

    @Step("Переход на странцу \"Проекты\".  Проверка отображения страницы \"Проекты\". Проверка видимости созданных в предусловии проектов")
    private void goToProjectPage() {
        getPage(HeaderPage.class).getProjects().click();
        Assert.assertEquals(getPage(HeaderPage.class).pageTitle(), "Проекты");
        Assert.assertTrue(getPage(ProjectsPage.class).getProjectElement(publicProject1.getName()));
        Assert.assertFalse(getPage(ProjectsPage.class).getProjectElement(privateProject2.getName()));
        Assert.assertTrue(getPage(ProjectsPage.class).getProjectElement(privateProject3.getName()));
    }

    @AfterMethod(description = "Закрытие браузера и выключение драйвера")
    public void tearDown() {
        Manager.driverQuit();
    }

}
