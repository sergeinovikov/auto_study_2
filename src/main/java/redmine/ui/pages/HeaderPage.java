package redmine.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Objects;

/**
 * Header компонент
 */

public class HeaderPage {
    private WebDriver driver;

    private WebElement home;
    private WebElement projects;
    private WebElement loggedAs;

    public HeaderPage(WebDriver driver) {
        Objects.requireNonNull(driver, "Драйвер должен быть проинициализирован");
        this.driver = driver;

        home = driver.findElement(By.xpath("//a[@class='home']"));
        projects = driver.findElement(By.xpath("//a[@class='projects']"));
        loggedAs = driver.findElement(By.xpath("//div[@id='loggedas']"));
    }

    public String loggedAs() {
        return loggedAs.getText();
    }
}
