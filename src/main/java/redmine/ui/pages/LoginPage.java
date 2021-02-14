package redmine.ui.pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Компонент авторизации
 */

@Getter
public class LoginPage extends AbstractPage {

    @FindBy(xpath = "//input[@id='username']")
    private WebElement loginElement;
    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordElement;
    @FindBy(xpath = "//input[@id='login-submit']")
    private WebElement submitElement;
    @FindBy(xpath = "//div[@id='flash_error']")
    private WebElement flashError;

    @FindBy(xpath = "//a[@class='login']")
    private WebElement login;
    @FindBy(xpath = "//a[@class='register']")
    private WebElement register;

    public void login(String login, String password) {
        loginElement.sendKeys(login);
        passwordElement.sendKeys(password);
        submitElement.click();
    }

    public String errorMessage() {
        return flashError.getText();
    }

    public String login() {
        return login.getText();
    }

    public String register() {
        return register.getText();
    }
}
