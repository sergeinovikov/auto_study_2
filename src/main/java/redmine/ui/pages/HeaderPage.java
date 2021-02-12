package redmine.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Header компонент
 */

public class HeaderPage extends AbstractPage {

    @FindBy(xpath = "//a[@class='home']")
    private WebElement home;
    @FindBy(xpath = "//a[@class='projects")
    private WebElement projects;
    @FindBy(xpath = "//div[@id='loggedas']")
    private WebElement loggedAs;

    public String loggedAs() {
        return loggedAs.getText();
    }
}
