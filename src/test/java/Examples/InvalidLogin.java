package Examples;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.managers.Manager;
import redmine.model.user.User;
import redmine.ui.pages.LoginPage;

import static redmine.ui.pages.Pages.getPage;

public class InvalidLogin {
    private User user;

    @BeforeMethod
    public void prepareFixtures() {
        user = new User().setStatus(1).generate();
        user.setLogin("1ddfdsddfsdf");
        user.setPassword("assa3r3d");

        Manager.openPage("login");
    }

    @Test
    public void myFirstLoginPage() {
        getPage(LoginPage.class)
                .login(user.getLogin(), user.getPassword());

        Assert.assertEquals(getPage(LoginPage.class).errorMessage(), "Неправильное имя пользователя или пароль");
    }

    @AfterMethod
    public void tearDown() {
        Manager.driverQuit();
    }
}
