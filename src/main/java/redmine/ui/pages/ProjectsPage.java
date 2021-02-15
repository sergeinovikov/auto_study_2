package redmine.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProjectsPage  extends AbstractPage{
    @FindBy(xpath = "//a")
    private WebElement projectElement;

    public String getProjectElement() {
        return projectElement.getText();
    }
}
