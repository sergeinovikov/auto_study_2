package ui_tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.managers.Manager;
import redmine.model.user.User;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;
import redmine.utils.BrowserUtils;

import static redmine.ui.pages.Pages.getPage;

public class UiTestCase2 {
    private User user;

    @BeforeMethod(description = "Генерация пользователя в Redmine. Пользователь подтверждён администратором (активен)")
    public void prepareFixtures() {
        user = new User()
                .setStatus(1)
                .generate();
    }

    @Test(description = "Кейс 2. Авторизация подтвержденным пользователем. Проверка присутствия элементов страницы после авторизации")
    @Step("Переход к странице авторизации. Авторизация пользователем из предусловия.")
    public void userLoginHomePageElementsCheck() {
        Manager.openPage("login");

        getPage(LoginPage.class)
                .login(user.getLogin(), user.getPassword());

        checkHomePage();
        checkLoggedAs();
        checkHeaderElements();
        checkLoginAndRegistrationAbsence();
        checkSearch();
    }

    @Description("Проверка 1. Проверка отображения домашней страницы")
    private void checkHomePage() {
        Assert.assertEquals(getPage(HeaderPage.class).pageTitle(), "Моя страница");
    }

    @Description("Проверка 2. Проверка отображения  \"Вошли как <логин пользователя>\"")
    private void checkLoggedAs() {
        Assert.assertEquals(getPage(HeaderPage.class).loggedAs(), String.format("Вошли как %s", user.getLogin()));
    }

    @Description("Проверка 3. Проверка отображения в заголовке страницы элементов \"Домашняя страница\", \"Моя страница\", \"Проекты\", \"Администрирование\", \"Помощь\", \"Моя учётная запись\", \"Выйти\"")
    private void checkHeaderElements() {
        Assert.assertEquals(getPage(HeaderPage.class).home(), "Домашняя страница");
        Assert.assertEquals(getPage(HeaderPage.class).myPage(), "Моя страница");
        Assert.assertEquals(getPage(HeaderPage.class).projects(), "Проекты");
        Assert.assertEquals(getPage(HeaderPage.class).help(), "Помощь");
        Assert.assertEquals(getPage(HeaderPage.class).myAccount(), "Моя учётная запись");
        Assert.assertEquals(getPage(HeaderPage.class).logout(), "Выйти");
    }

    @Description("Проверка 4. Проверка отсутствия в заголовке страницы элементов  \"Администрирование\", \"Войти\", \"Регистрация\"")
    private void checkLoginAndRegistrationAbsence() {
        Assert.assertFalse(
                BrowserUtils.isElementPresent(
                        getPage(HeaderPage.class).getAdministration()
                )
        );
        Assert.assertFalse(
                BrowserUtils.isElementPresent(
                        getPage(LoginPage.class).getLoginElement()
                )
        );
        Assert.assertFalse(
                BrowserUtils.isElementPresent(
                        getPage(LoginPage.class).getRegister()
                )
        );
    }

    @Description("Проверка 5. Проверка отображения элемента \"Поиск\"")
    private void checkSearch() {
        Assert.assertEquals(getPage(HeaderPage.class).searchLabel(), "Поиск");
        Assert.assertTrue(getPage(HeaderPage.class).searchInput());
    }

    @AfterMethod(description = "Закрытие браузера и выключение драйвера")
    public void tearDown() {
        Manager.driverQuit();
    }
}
