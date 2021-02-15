package ui_tests;

import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.managers.Manager;
import redmine.model.project.Project;
import redmine.model.user.User;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;
import redmine.ui.pages.ProjectsPage;
import redmine.utils.BrowserUtils;

import static redmine.ui.pages.Pages.getPage;

public class TestCase4 {
    private User user;
    private Project project;

    @BeforeMethod(description = "Генерация пользователя в система. Пользователь не подтверждён администратором (не активен)")
    public void prepareFixtures() {
        user = new User()
                .setAdmin(true)
                .setStatus(1)
                .generate();
        project = new Project()
                .setStatus(1)
                .setIsPublic(false)
                .generate();
    }

    @Test(description = "Кейс 4. Видимость проекта. Приватный проект. Администратор")
    public void adminPrivateProjectCheck() {
        adminlogin();
        goToProjectPage();
    }

    @Step("Авторизация пользователем с ролью администратор")
    private void adminlogin(){
        Manager.openPage("login");

        getPage(LoginPage.class)
                .login(user.getLogin(), user.getPassword());

        Assert.assertTrue(BrowserUtils.isElementPresent(getPage(HeaderPage.class).getHomePage()));
    }

    @Step("Авторизация пользователем с ролью администратор")
    private void goToProjectPage(){
        getPage(HeaderPage.class).getProjects().click();
        Assert.assertEquals(getPage(HeaderPage.class).pageTitle(), "Проекты");
        Assert.assertEquals(getPage(ProjectsPage.class).getProjectElement(), project.getName()); // доработать
    }
}
