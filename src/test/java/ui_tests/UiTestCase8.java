package ui_tests;

import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.managers.Manager;
import redmine.model.dto.UserDto;
import redmine.model.dto.UserInfo;
import redmine.model.user.User;
import redmine.ui.pages.*;
import redmine.utils.StringGenerators;

import static redmine.model.dto.UserDto.readUserDtoByLogin;
import static redmine.ui.pages.Pages.getPage;

public class UiTestCase8 {
    private User mainUser;
    private UserDto userDto;

    @BeforeMethod(description = "Генерация пользователя с правами администратора в Redmine")
    public void prepareFixtures() {
        mainUser = new User()
                .setAdmin(true)
                .setStatus(1)
                .generate();
    }

    @Test(description = "Кейс 8. Авторизация пользователя с правами администратора.  Переход на страницы \"Админитрирование\" и \"Пользователи\". Создание пользователя на клиентской части. Проверка созданного пользователя в базе данных")
    public void userCreation() {
        userLogin();
        goToAdminPage();
        goToNewUserPage();
        fillInUserData();
        setGenerateUserPassword();
        createNewUser();
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

    @Step("Переход на странцу \"Пользователи\".  Переход на странцу \"Новый пользователь\". Проверка отображения страницы создания нового пользователя")
    private void goToNewUserPage() {
        getPage(AdministrationPage.class).getUsers().click();
        Assert.assertEquals(getPage(HeaderPage.class).pageTitle(), "Пользователи");

        getPage(UsersPage.class).getNewUser().click();
        Assert.assertEquals(getPage(HeaderPage.class).pageTitle(), "Пользователи » Новый пользователь");
    }

    @Step("Заполнение полей \"Пользователь\", \"Имя\", \"Фамилия\", \"Email\" корректными значениями")
    private void fillInUserData() {
        String login = "SN" + StringGenerators.randomEnglishLowerString(8);
        String firstName = "Ser" + StringGenerators.randomEnglishString(8);
        String lastName = "Nov" + StringGenerators.randomEnglishString(8);
        String mail = StringGenerators.randomEmail(8);

        userDto = new UserDto()
                .setUser(new UserInfo()
                        .setLogin(login)
                        .setFirstname(firstName)
                        .setLastname(lastName)
                        .setMail(mail)
                );

        getPage(NewUserPage.class).fillInUserData(userDto);
    }

    @Step("Проставление чекбокса \"Создание пароля\"")
    private void setGenerateUserPassword() {
        getPage(NewUserPage.class).getGenerateUserPassword().click();
    }

    @Step("Создание нового пользователя. Проерка сообщения о создании нового пользователя. Проверка наличия созданного пользователя в базе данных")
    private void createNewUser() {
        getPage(NewUserPage.class).getCreateButton().click();

        Assert.assertEquals(getPage(NewUserPage.class).successMessage(), "Пользователь " + userDto.getUser().getLogin() + " создан.");

        User userFromDb = readUserDtoByLogin(userDto.getUser().getLogin());

        Assert.assertNotNull(userFromDb.getId());
        Assert.assertEquals(userDto.getUser().getLogin(), userFromDb.getLogin());
        Assert.assertEquals(userDto.getUser().getFirstname(), userFromDb.getFirstName());
        Assert.assertEquals(userDto.getUser().getLastname(), userFromDb.getLastName());
        Assert.assertEquals(userDto.getUser().getMail(), userFromDb.getEmail().getAddress());
    }

    @AfterMethod(description = "Закрытие браузера и выключение драйвера")
    public void tearDown() {
        Manager.driverQuit();
    }
}
