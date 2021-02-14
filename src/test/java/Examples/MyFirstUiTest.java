package Examples;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.managers.Manager;
import redmine.model.user.User;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;

import static redmine.ui.pages.Pages.getPage;

public class MyFirstUiTest {
    private User user;

    @BeforeMethod
    public void prepareFixtures() {
        user = new User().setStatus(1).generate();

        Manager.openPage("login");
    }

    @Test
    public void myFirstLoginPage() {
        getPage(LoginPage.class)
                .login(user.getLogin(), user.getPassword());

        Assert.assertEquals(getPage(HeaderPage.class).loggedAs(), String.format("Вошли как %s", user.getLogin()));
    }

    @AfterMethod
    public void tearDown() {
        Manager.driverQuit();
    }
}
