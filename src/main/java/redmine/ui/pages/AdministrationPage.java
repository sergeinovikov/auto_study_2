package redmine.ui.pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.ui.pages.helpers.CucumberName;

@Getter
public class AdministrationPage extends AbstractPage{

    @CucumberName("Пользователи")
    @FindBy(xpath = "//a[@href='/users']")
    private WebElement users;

}
