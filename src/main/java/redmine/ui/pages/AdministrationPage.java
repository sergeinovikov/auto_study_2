package redmine.ui.pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class AdministrationPage extends AbstractPage{
    @FindBy(xpath = "//a[@href='/users']")
    private WebElement users;

}
