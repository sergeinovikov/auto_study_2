package redmine.ui.pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.ui.pages.helpers.CucumberName;

/**
 * Header компонент
 */

@Getter
@CucumberName("Заголовок")
public class HeaderPage extends AbstractPage {
    @CucumberName("Домашняя страница")
    @FindBy(xpath = "//a[@class='home']")
    private WebElement home;

    @CucumberName("Проекты")
    @FindBy(xpath = "//a[@class='projects']")
    private WebElement projects;

    @CucumberName("Вошли как")
    @FindBy(xpath = "//div[@id='loggedas']")
    private WebElement loggedAs;

    @CucumberName("Моя страница")
    @FindBy(xpath = "//a[@class='my-page']")
    private WebElement myPage;

    @CucumberName("Администрирование")
    @FindBy(xpath = "//a[@class='administration']")
    private WebElement administration;

    @CucumberName("Помощь")
    @FindBy(xpath = "//a[@class='help']")
    private WebElement help;

    @CucumberName("Моя учётная запись")
    @FindBy(xpath = "//a[@class='my-account']")
    private WebElement myAccount;

    @CucumberName("Выйти")
    @FindBy(xpath = "//a[@class='logout']")
    private WebElement logout;

    @CucumberName("Имя поиска")
    @FindBy(xpath = "//a[@href='/search']")
    private WebElement searchLabel;

    @CucumberName("Поиск")
    @FindBy(xpath = "//input[@id='q']")
    private WebElement searchInput;

    @CucumberName("Войти")
    @FindBy(xpath = "//a[@class='login']")
    private WebElement login;

    @CucumberName("Регистрация")
    @FindBy(xpath = "//a[@class='register']")
    private WebElement register;

    @CucumberName("Заголовок страницы")
    @FindBy(xpath = "//h2")
    private WebElement pageTitle;


    public String loggedAs() {
        return loggedAs.getText();
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

    public String login() {
        return login.getText();
    }

    public String register() {
        return register.getText();
    }

    public String pageTitle() {
        return pageTitle.getText();
    }
}
