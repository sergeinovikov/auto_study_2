package ui_tests;

import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.managers.Manager;
import redmine.model.project.Project;
import redmine.model.user.User;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;
import redmine.ui.pages.ProjectsPage;

import static redmine.ui.pages.Pages.getPage;

public class UiTestCase4 {
    private User user;
    private Project project;

    @BeforeMethod(description = "Генерация пользователя с правами администратора в Redmine. Пользователь подтверждён и активен")
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

    @Test(description = "Кейс 4. Авторизация пользователем с правами администратора. Проверка видимости приватного проекта")
    public void adminPrivateProjectCheck() {
        adminLogin();
        goToProjectPage();
    }

    @Step("Авторизация пользователем с правами администратор. Проверка отображения домашней страницы")
    private void adminLogin() {
        Manager.openPage("login");

        getPage(LoginPage.class)
                .login(user.getLogin(), user.getPassword());

        Assert.assertEquals(getPage(HeaderPage.class).pageTitle(), "Моя страница");
    }

    @Step("Переход на странцу \"Проекты\". Проверка отображения страницы \"Проекты\". Проверка видимости созданного в предусловии приватного проекта.")
    private void goToProjectPage() {
        getPage(HeaderPage.class).getProjects().click();

        Assert.assertEquals(getPage(HeaderPage.class).pageTitle(), "Проекты");

        Assert.assertTrue(
                getPage(ProjectsPage.class).getProjectElement(
                        project.getName()
                )
        );
    }

    @AfterMethod(description = "Закрытие браузера и выключение драйвера")
    public void tearDown() {
        Manager.driverQuit();
    }
}
