package ui_tests;

import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.managers.Manager;
import redmine.model.user.User;
import redmine.ui.pages.AdministrationPage;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;
import redmine.ui.pages.UsersPage;
import redmine.utils.BrowserUtils;

import static redmine.ui.pages.Pages.getPage;

public class UiTestCase6 {
    private User mainUser;
    private User anotherUser1;
    private User anotherUser2;
    private User anotherUser3;
    private User anotherUser4;
    private User anotherUser5;

    @BeforeMethod(description = "Генерация пользователя с правами администратора в Redmine. Генерация нескольких подтверждённых пользователей")
    public void prepareFixtures() {
        mainUser = new User()
                .setAdmin(true)
                .setStatus(1)
                .generate();
        anotherUser1 = new User()
                .setStatus(1)
                .generate();
        anotherUser2 = new User()
                .setStatus(1)
                .generate();
        anotherUser3 = new User()
                .setStatus(1)
                .generate();
        anotherUser4 = new User()
                .setStatus(1)
                .generate();
        anotherUser5 = new User()
                .setStatus(1)
                .generate();
    }

    @Test(description = "Кейс 6. Авторизация пользователя с правами администратора. Проверка видимости приватного проекта 3 с ролью, которая имеет доступ только к этому проекту")
    public void userWithRoleProjectsCheck() {
        userLogin();
        goToAdminPage();
        goToUsersPage();
        sortUsersByLoginDesc();
    }

    @Step("Авторизация пользователем с правами администратора и ролью из предусловия. Проверка отображения домашней страницы")
    private void userLogin() {
        Manager.openPage("login");

        getPage(LoginPage.class)
                .login(mainUser.getLogin(), mainUser.getPassword());

        Assert.assertEquals(getPage(HeaderPage.class).pageTitle(), "Моя страница");
    }

    @Step("Переход на странцу \"Администрирование\".  Проверка отображения страницы \"Администрирование\"")
    private void goToAdminPage() {
        getPage(HeaderPage.class).getAdministration().click();
        Assert.assertEquals(getPage(HeaderPage.class).pageTitle(), "Администрирование");
    }

    @Step("Переход на странцу \"Пользователи\".  Проверка отображения таблицы \"Пользователи\" и сортировки пользователей по логину (по возрастанию)")
    private void goToUsersPage() {
        getPage(AdministrationPage.class).getUsers().click();
        Assert.assertEquals(getPage(HeaderPage.class).pageTitle(), "Пользователи");
        Assert.assertTrue(BrowserUtils.isElementPresent(getPage(UsersPage.class).getUsersTable()));
        getPage(UsersPage.class).usersSortedByLoginAsc();
    }

    @Step("Нажатие в шапке таблицы на столбец \"Пользователь\". Проверка сортировки пользователей по логину (по убыыванию)")
    private void sortUsersByLoginDesc() {
        getPage(UsersPage.class).getSortingUsersByLogin().click();
        getPage(UsersPage.class).usersSortedByLoginDesc();
    }

    @AfterMethod(description = "Закрытие браузера и выключение драйвера")
    public void tearDown() {
        Manager.driverQuit();
    }
}
