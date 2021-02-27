package ui_tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.managers.Manager;
import redmine.model.user.User;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;
import redmine.utils.Asserts;
import redmine.utils.BrowserUtils;

import static redmine.ui.pages.Pages.getPage;

public class UiTestCase1 {
    private User user;

    @BeforeMethod(description = "Генерация пользователя с правами администратора в Redmine. Пользователь подтверждён и активен")
    public void prepareFixtures() {
        user = new User()
                .setAdmin(true)
                .setStatus(1)
                .generate();
    }

    @Test(description = "Кейс 1. Авторизация пользователем с правами администратора. Проверка присутствия элементов страницы после авторизации")
    @Step("Переход к странице авторизации. Авторизация пользователем из предусловия.")
    public void adminLoginHomeElementsCheck() {
        Manager.openPage("login");

        getPage(LoginPage.class)
                .login(user.getLogin(), user.getPassword());

        checkHomePage();
        checkLoggedAs();
        checkHeaderElements();
        checkLoginAndRegistrationAbsence();
        checkSearch();
    }

    private void checkHomePage() {
        Allure.step("Проверка отображения домашней страницы");
        Asserts.assertEquals(getPage(HeaderPage.class).pageTitle(), "Моя страница");
        Manager.takesScreenshot();
    }

    private void checkLoggedAs() {
        Allure.step("Проверка отображения  \"Вошли как <логин пользователя>\"");
        Asserts.assertEquals(getPage(HeaderPage.class).loggedAs(), String.format("Вошли как %s", user.getLogin()));
    }

    private void checkHeaderElements() {
        Allure.step("Проверка отображения в заголовке страницы элементов \"Домашняя страница\", \"Моя страница\", \"Проекты\", \"Администрирование\", \"Помощь\", \"Моя учётная запись\", \"Выйти\"");
        Asserts.assertEquals(getPage(HeaderPage.class).home(), "Домашняя страница");
        Asserts.assertEquals(getPage(HeaderPage.class).myPage(), "Моя страница");
        Asserts.assertEquals(getPage(HeaderPage.class).projects(), "Проекты");
        Asserts.assertEquals(getPage(HeaderPage.class).administration(), "Администрирование");
        Asserts.assertEquals(getPage(HeaderPage.class).help(), "Помощь");
        Asserts.assertEquals(getPage(HeaderPage.class).myAccount(), "Моя учётная запись");
        Asserts.assertEquals(getPage(HeaderPage.class).logout(), "Выйти");
    }

    private void checkLoginAndRegistrationAbsence() {
        Allure.step("Проверка отсутствия в заголовке страницы элементов \"Войти\", \"Регистрация\"");
        Asserts.assertFalse(
                BrowserUtils.isElementPresent(
                        getPage(LoginPage.class).getLoginElement()
                )
        );
        Asserts.assertFalse(
                BrowserUtils.isElementPresent(
                        getPage(LoginPage.class).getRegister()
                )
        );
    }

    private void checkSearch() {
        Allure.step("Проверка отображения элемента \"Поиск\"");
        Asserts.assertEquals(getPage(HeaderPage.class).searchLabel(), "Поиск");
        Asserts.assertTrue(getPage(HeaderPage.class).searchInput());
    }

    @AfterMethod(description = "Закрытие браузера и выключение драйвера")
    public void tearDown() {
        Manager.driverQuit();
    }
}
