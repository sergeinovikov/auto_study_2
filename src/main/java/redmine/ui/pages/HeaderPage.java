package redmine.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import redmine.managers.Manager;

/**
 * Header компонент
 */

public class HeaderPage {

    @FindBy(xpath="//a[@class='home']")
    private WebElement home;
    @FindBy(xpath="//a[@class='projects")
    private WebElement projects;
    @FindBy(xpath="//div[@id='loggedas']")
    private WebElement loggedAs;

    public HeaderPage() {
        PageFactory.initElements(Manager.driver(), this);
    }

    public String loggedAs() {
        return loggedAs.getText();
    }
}
