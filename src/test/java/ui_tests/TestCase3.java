package ui_tests;

import io.qameta.allure.Description;
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

public class TestCase3 {
    private User user;

    @BeforeMethod(description = "Генерация пользователя в система. Пользователь не подтверждён администратором (не активен)")
    public void prepareFixtures() {
        user = new User()
                .setStatus(2)
                .generate();
    }

    @Test(description = "Кейс 3. Авторизация не подтвержденным пользователем. Проверка присутствия ошибки и элементов страницы после авторизации")
    public void adminLogin() {
        Manager.openPage("login");

        getPage(LoginPage.class)
                .login(user.getLogin(), user.getPassword());

        checkHomePageAbsence();
        checkErrorMessage();
        checkMyPage();
        checkLoginAndRegister();
    }

    @Description("Проверка 1. Проверка отсутствия отображения домашней страницы")
    private void checkHomePageAbsence() {
        Assert.assertFalse(BrowserUtils.isElementPresent(getPage(HeaderPage.class).getHomePage()));
    }

    @Description("Проверка 2. Проверка отображения ошибки с текстом \"Ваша учётная запись создана и ожидает подтверждения администратора.\"")
    private void checkErrorMessage() {
        Assert.assertEquals(getPage(LoginPage.class).errorMessage(), "Ваша учётная запись создана и ожидает подтверждения администратора.");
    }

    @Description("Проверка 3. Проверка отсутствия в заголовке страницы элемента \"Моя страница\"")
    private void checkMyPage() {
        Assert.assertFalse(BrowserUtils.isElementPresent(getPage(HeaderPage.class).getMyPage()));
    }

    @Description("Проверка 4. Проверка отображения в заголовке страницы элементов  \"Войти\", \"Регистрация\"")
    private void checkLoginAndRegister() {
        Assert.assertEquals(getPage(LoginPage.class).login(), "Войти");
        Assert.assertEquals(getPage(LoginPage.class).register(), "Регистрация");
    }

    @AfterMethod(description = "Закрытие браузера и выключение драйвера")
    public void tearDown() {
        Manager.driverQuit();
    }
}
