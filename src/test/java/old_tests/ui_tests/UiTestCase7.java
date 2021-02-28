package old_tests.ui_tests;

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
import redmine.utils.Asserts;
import redmine.utils.BrowserUtils;

import static redmine.ui.pages.Pages.getPage;

public class UiTestCase7 {
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

    @Test(description = "Кейс 7. Авторизация пользователя с правами администратора.  Переход на страницы \"Админитрирование\" и \"Пользователи\". Проверка наличия таблицы пользователей и сортировки пользователей по фамилии и имени")
    public void sortingUsersByFirstAndLastNames() {
        userLogin();
        goToAdminPage();
        goToUsersPage();
        sortUsersByLastNameAsc();
        sortUsersByLastNameDesc();
        sortUsersByFirstNameAsc();
        sortUsersByFirstNameDesc();
    }

    @Step("Авторизация пользователем с правами администратора. Проверка отображения домашней страницы")
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

    @Step("Переход на странцу \"Пользователи\".  Проверка отображения таблицы \"Пользователи\" и отсутствия сортировки пользователей по имени и фамилии")
    private void goToUsersPage() {
        getPage(AdministrationPage.class).getUsers().click();

        Asserts.assertEquals(getPage(HeaderPage.class).pageTitle(), "Пользователи");
        Asserts.assertTrue(
                BrowserUtils.isElementPresent(
                        getPage(UsersPage.class).getUsersTable()
                )
        );

        Asserts.assertFalse(
                getPage(UsersPage.class).usersSortedAsc(
                        getPage(UsersPage.class).getUsersFirstNames()
                )
        );
        Asserts.assertFalse(
                getPage(UsersPage.class).usersSortedDesc(
                        getPage(UsersPage.class).getUsersFirstNames()
                )
        );

        Asserts.assertFalse(
                getPage(UsersPage.class).usersSortedAsc(
                        getPage(UsersPage.class).getUsersLastNames()
                )
        );
        Asserts.assertFalse(
                getPage(UsersPage.class).usersSortedDesc(
                        getPage(UsersPage.class).getUsersLastNames()
                )
        );
    }

    @Step("Нажатие в шапке таблицы на столбец \"Фамилия\". Проверка сортировки пользователей по фамилии (по возрастанию, регистр не учитывается) и отсутствия сортировки по имени")
    private void sortUsersByLastNameAsc() {
        getPage(UsersPage.class).getSortingUsersByLastName().click();

        Asserts.assertTrue(
                getPage(UsersPage.class).usersSortedAsc(
                        getPage(UsersPage.class).getUsersLastNames()
                )
        );

        Asserts.assertFalse(
                getPage(UsersPage.class).usersSortedAsc(
                        getPage(UsersPage.class).getUsersFirstNames()
                )
        );
        Asserts.assertFalse(
                getPage(UsersPage.class).usersSortedDesc(
                        getPage(UsersPage.class).getUsersFirstNames()
                )
        );
    }

    @Step("Нажатие в шапке таблицы на столбец \"Фамилия\". Проверка сортировки пользователей по фамилии (по убыванию, регистр не учитывается) и отсутствия сортировки по имени")
    private void sortUsersByLastNameDesc() {
        getPage(UsersPage.class).getSortingUsersByLastName().click();

        Asserts.assertTrue(
                getPage(UsersPage.class).usersSortedDesc(
                        getPage(UsersPage.class).getUsersLastNames()
                )
        );

        Asserts.assertFalse(
                getPage(UsersPage.class).usersSortedAsc(
                        getPage(UsersPage.class).getUsersFirstNames()
                )
        );
        Asserts.assertFalse(
                getPage(UsersPage.class).usersSortedDesc(
                        getPage(UsersPage.class).getUsersFirstNames()
                )
        );
    }

    @Step("Нажатие в шапке таблицы на столбец \"Имя\". Проверка сортировки пользователей по имени (по возрастанию, регистр не учитывается) и отсутствия сортировки по фамилии")
    private void sortUsersByFirstNameAsc() {
        getPage(UsersPage.class).getSortingUsersByFirstName().click();

        Assert.assertFalse(
                getPage(UsersPage.class).usersSortedAsc(
                        getPage(UsersPage.class).getUsersLastNames()
                )
        );
        Assert.assertFalse(
                getPage(UsersPage.class).usersSortedDesc(
                        getPage(UsersPage.class).getUsersLastNames()
                )
        );

        Assert.assertTrue(
                getPage(UsersPage.class).usersSortedAsc(
                        getPage(UsersPage.class).getUsersFirstNames()
                )
        );
    }

    @Step("Нажатие в шапке таблицы на столбец \"Имя\". Проверка сортировки пользователей по имени (по убыванию, регистр не учитывается) и отсутствия сортировки по фамилии")
    private void sortUsersByFirstNameDesc() {
        getPage(UsersPage.class).getSortingUsersByFirstName().click();

        Assert.assertFalse(
                getPage(UsersPage.class).usersSortedAsc(
                        getPage(UsersPage.class).getUsersLastNames()
                )
        );
        Assert.assertFalse(
                getPage(UsersPage.class).usersSortedDesc(
                        getPage(UsersPage.class).getUsersLastNames()
                )
        );

        Assert.assertTrue(
                getPage(UsersPage.class).usersSortedDesc(
                        getPage(UsersPage.class).getUsersFirstNames()
                )
        );
    }

    @AfterMethod(description = "Закрытие браузера и выключение драйвера")
    public void tearDown() {
        Manager.driverQuit();
    }
}
