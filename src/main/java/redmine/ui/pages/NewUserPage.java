package redmine.ui.pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.model.dto.UserDto;
import redmine.model.user.User;
import redmine.ui.pages.helpers.CucumberName;

@Getter
@CucumberName("Новый пользователь")
public class NewUserPage extends AbstractPage {

    @CucumberName("Логин")
    @FindBy(xpath = "//input[@id='user_login']")
    private WebElement userLogin;

    @CucumberName("Имя")
    @FindBy(xpath = "//input[@id='user_firstname']")
    private WebElement userFirstName;

    @CucumberName("Фамилия")
    @FindBy(xpath = "//input[@id='user_lastname']")
    private WebElement userLastName;

    @CucumberName("Почта")
    @FindBy(xpath = "//input[@id='user_mail']")
    private WebElement userMail;

    @CucumberName("Задать пароль")
    @FindBy(xpath = "//input[@id='user_generate_password']")
    private WebElement generateUserPassword;

    @CucumberName("Создать")
    @FindBy(xpath = "//input[@name='commit']")
    private WebElement createButton;

    @CucumberName("Уведомление")
    @FindBy(xpath = "//div[@id='flash_notice']")
    private WebElement creationSuccessful;


    public void fillInUserData(User userBeforeCreation) {
        userLogin.sendKeys(userBeforeCreation.getLogin());
        userFirstName.sendKeys(userBeforeCreation.getFirstName());
        userLastName.sendKeys(userBeforeCreation.getLastName());
        userMail.sendKeys(userBeforeCreation.getEmail().getAddress());
    }

    public String successMessage() {
        return creationSuccessful.getText();
    }

}
