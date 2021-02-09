package ui_tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.managers.Manager;
import redmine.model.user.User;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;

public class MyFirstUiTest {
    private User user;

    @BeforeMethod
    public void prepareFixtures() {
        user = new User().setStatus(1).generate();

        Manager.openPage("login");
    }

    @Test
    public void myFirstLoginPage() {
        new LoginPage()
                .login(user.getLogin(),user.getPassword());

        Assert.assertEquals(new HeaderPage().loggedAs(), String.format("Вошли как %s", user.getLogin()));
    }

    @AfterMethod
    public void tearDown() {
        Manager.driverQuit();
    }
}
