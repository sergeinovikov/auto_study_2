package old_tests.ui_tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.testng.Assert;
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

    private void checkHomePage() {
        Allure.step("Проверка отображения домашней страницы");
        Assert.assertEquals(getPage(HeaderPage.class).pageTitle(), "Моя страница");
    }

    private void checkLoggedAs() {
        Allure.step("Проверка отображения  \"Вошли как <логин пользователя>\"");
        Assert.assertEquals(getPage(HeaderPage.class).loggedAs(), String.format("Вошли как %s", user.getLogin()));
    }

    private void checkHeaderElements() {
        Allure.step("Проверка отображения в заголовке страницы элементов \"Домашняя страница\", \"Моя страница\", \"Проекты\", \"Администрирование\", \"Помощь\", \"Моя учётная запись\", \"Выйти\"");
        Assert.assertEquals(getPage(HeaderPage.class).home(), "Домашняя страница");
        Assert.assertEquals(getPage(HeaderPage.class).myPage(), "Моя страница");
        Assert.assertEquals(getPage(HeaderPage.class).projects(), "Проекты");
        Assert.assertEquals(getPage(HeaderPage.class).help(), "Помощь");
        Assert.assertEquals(getPage(HeaderPage.class).myAccount(), "Моя учётная запись");
        Assert.assertEquals(getPage(HeaderPage.class).logout(), "Выйти");
    }

    private void checkLoginAndRegistrationAbsence() {
        Allure.step("Проверка отсутствия в заголовке страницы элементов  \"Администрирование\", \"Войти\", \"Регистрация\"");
        Asserts.assertFalse(
                BrowserUtils.isElementPresent(
                        getPage(HeaderPage.class).getAdministration()
                )
        );
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
