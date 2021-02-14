package redmine.ui.pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Header компонент
 */

@Getter
public class HeaderPage extends AbstractPage {

    @FindBy(xpath = "//a[@class='home']")
    private WebElement home;
    @FindBy(xpath = "//a[@class='projects']")
    private WebElement projects;
    @FindBy(xpath = "//div[@id='loggedas']")
    private WebElement loggedAs;
    @FindBy(xpath = "//h2")
    private WebElement homePage;
    @FindBy(xpath = "//a[@class='my-page']")
    private WebElement myPage;
    @FindBy(xpath = "//a[@class='administration']")
    private WebElement administration;
    @FindBy(xpath = "//a[@class='help']")
    private WebElement help;
    @FindBy(xpath = "//a[@class='my-account']")
    private WebElement myAccount;
    @FindBy(xpath = "//a[@class='logout']")
    private WebElement logout;
    @FindBy(xpath = "//a[@href='/search']")
    private WebElement searchLabel;
    @FindBy(xpath = "//input[@id='q']")
    private WebElement searchInput;


    public String loggedAs() {
        return loggedAs.getText();
    }

    public String homePageHeader() {
        return homePage.getText();
    }

    public String home() {
        return home.getText();
    }

    public String myPage() {
        return myPage.getText();
    }

    public String projects() {
        return projects.getText();
    }

    public String administration() {
        return administration.getText();
    }

    public String help() {
        return help.getText();
    }

    public String myAccount() {
        return myAccount.getText();
    }

    public String logout() {
        return logout.getText();
    }

    public String searchLabel() {
        return searchLabel.getText();
    }

    public Boolean searchInput() {
        return searchInput.isDisplayed();
    }

}
