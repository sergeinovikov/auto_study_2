package redmine.ui.pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.model.dto.UserDto;

@Getter
public class NewUserPage extends AbstractPage {
    @FindBy(xpath = "//input[@id='user_login']")
    private WebElement userLogin;
    @FindBy(xpath = "//input[@id='user_firstname']")
    private WebElement userFirstName;
    @FindBy(xpath = "//input[@id='user_lastname']")
    private WebElement userLastName;
    @FindBy(xpath = "//input[@id='user_mail']")
    private WebElement userMail;
    @FindBy(xpath = "//input[@id='user_generate_password']")
    private WebElement generateUserPassword;
    @FindBy(xpath = "//input[@name='commit']")
    private WebElement createButton;
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
