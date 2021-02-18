package redmine.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProjectsPage  extends AbstractPage{
    @FindBy(xpath = "//ul[@class='projects root']//a")
    private List<WebElement> projectsList;

    public Boolean getProjectElement(String projectName) {
        return projectsList.stream()
                .map(WebElement::getText)
                .anyMatch(str -> str.equals(projectName));
    }
}
