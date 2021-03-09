package steps;

import cucumber.api.java.ru.То;
import redmine.cucumber.ParametersValidator;
import redmine.ui.pages.ProjectsPage;
import redmine.ui.pages.helpers.Pages;
import redmine.utils.Asserts;

public class ProjectsSteps {

    @То("На странице отображается проект {string}")
    public void assertProjectIsDisplayed(String rawProjectName) {
        String projectName = ParametersValidator.replaceCucumberVariables(rawProjectName);

        Asserts.assertTrue(
                Pages.getPage(ProjectsPage.class).getProjectElement(projectName)
        );
    }

    @То("На странице не отображается проект {string}")
    public void assertProjectIsNotDisplayed(String rawProjectName) {
        String projectName = ParametersValidator.replaceCucumberVariables(rawProjectName);

        Asserts.assertFalse(
                Pages.getPage(ProjectsPage.class).getProjectElement(projectName)
        );
    }
}
