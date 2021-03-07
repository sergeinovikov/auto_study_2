package redmine.ui.pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.ui.pages.helpers.CucumberName;

/**
 * Компонент авторизации
 */

@Getter
@CucumberName("Страница авторизации")
public class LoginPage extends AbstractPage {

    @CucumberName("Логин")
    @FindBy(xpath = "//input[@id='username']")
    private WebElement username;

    @CucumberName("Пароль")
    @FindBy(xpath = "//input[@id='password']")
    private WebElement password;

    @CucumberName("Вход")
    @FindBy(xpath = "//input[@id='login-submit']")
    private WebElement submitElement;

    @CucumberName("Сообщение об ошибке")
    @FindBy(xpath = "//div[@id='flash_error']")
    private WebElement flashError;


    public void login(String login, String password) {
        username.sendKeys(login);
        this.password.sendKeys(password);
        submitElement.click();
    }

    public String errorMessage() {
        return flashError.getText();
    }

}
