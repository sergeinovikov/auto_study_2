package ui_tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.Property;
import redmine.model.user.User;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;

public class MyFirstUiTest {
    private WebDriver driver;
    private User user;

    @BeforeMethod
    public void prepareFixtures() {
        user = new User().setStatus(1).generate();

        System.setProperty("webdriver.chrome.driver", Property.getStringProperty("webdriver.chrome.driver"));
        driver = new ChromeDriver();

        driver.get(Property.getStringProperty("ui.url") + "/login");
    }

    @Test
    public void myFirstLoginPage() {
        new LoginPage(driver)
                .login(user.getLogin(),user.getPassword());

        Assert.assertEquals(new HeaderPage(driver).loggedAs(), String.format("Вошли как %s", user.getLogin()));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
