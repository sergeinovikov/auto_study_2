package old_tests.ui_tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
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

public class UiTestCase3 {
    private User user;

    @BeforeMethod(description = "Генерация пользователя в Redmine. Пользователь не подтверждён администратором (не активен)")
    public void prepareFixtures() {
        user = new User()
                .setStatus(2)
                .generate();
    }

    @Test(description = "Кейс 3. Авторизация не подтвержденным пользователем. Проверка присутствия ошибки и элементов страницы после авторизации")
    @Step("Переход к странице авторизации. Авторизация пользователем из предусловия.")
    public void unverifiedUserLogin() {
        Manager.openPage("login");

        getPage(LoginPage.class)
                .login(user.getLogin(), user.getPassword());

        checkHomePageAbsence();
        checkErrorMessage();
        checkMyPage();
        checkLoginAndRegister();
    }

    @Description("Проверка 1. ")
    private void checkHomePageAbsence() {
        Allure.step("Проверка отображения домашней страницы");
        Asserts.assertTrue(
                BrowserUtils.isElementPresent(
                        getPage(LoginPage.class).getLoginElement()
                )
        );
        Asserts.assertTrue(
                BrowserUtils.isElementPresent(
                        getPage(LoginPage.class).getPasswordElement()
                )
        );
        Asserts.assertTrue(
                BrowserUtils.isElementPresent(
                        getPage(LoginPage.class).getSubmitElement()
                )
        );
    }

    private void checkErrorMessage() {
        Allure.step("Проверка отображения ошибки с текстом \\\"Ваша учётная запись создана и ожидает подтверждения администратора.\\\"");
        Asserts.assertEquals(getPage(LoginPage.class).errorMessage(), "Ваша учётная запись создана и ожидает подтверждения администратора.");
    }

    private void checkMyPage() {
        Allure.step("Проверка отсутствия в заголовке страницы элемента \"Моя страница\"");
        Asserts.assertFalse(
                BrowserUtils.isElementPresent(
                        getPage(HeaderPage.class).getMyPage()
                )
        );
    }

    private void checkLoginAndRegister() {
        Allure.step("Проверка отображения в заголовке страницы элементов  \"Войти\", \"Регистрация\"");
        Asserts.assertEquals(getPage(LoginPage.class).login(), "Войти");
        Asserts.assertEquals(getPage(LoginPage.class).register(), "Регистрация");
    }

    @AfterMethod(description = "Закрытие браузера и выключение драйвера")
    public void tearDown() {
        Manager.driverQuit();
    }
}
