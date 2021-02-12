package redmine.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Компонент авторизации
 */

public class LoginPage extends AbstractPage{

    @FindBy(xpath="//input[@id='username']")
    private WebElement loginElement;
    @FindBy(xpath="//input[@id='password']")
    private WebElement passwordElement;
    @FindBy(xpath="//input[@id='login-submit']")
    private WebElement submitElement;

    public void login (String login, String password) {
        loginElement.sendKeys(login);
        passwordElement.sendKeys(password);
        submitElement.click();
    }
}
