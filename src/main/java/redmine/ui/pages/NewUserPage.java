package redmine.ui.pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.model.dto.UserDto;
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


    public void fillInUserData(UserDto userDto) {
        userLogin.sendKeys(userDto.getUser().getLogin());
        userFirstName.sendKeys(userDto.getUser().getFirstname());
        userLastName.sendKeys(userDto.getUser().getLastname());
        userMail.sendKeys(userDto.getUser().getMail());
    }

    public String successMessage() {
        return creationSuccessful.getText();
    }

}
