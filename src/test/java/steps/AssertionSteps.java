package steps;

import cucumber.api.java.ru.То;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import redmine.managers.Context;
import redmine.ui.pages.helpers.CucumberPageObjectHelper;
import redmine.utils.BrowserUtils;

public class AssertionSteps {

    @То("Значение переменной {string} будет равно {int}")
    public void assertResult(String stashId, Integer expectedResult) {
        Integer actualResult = Context.getStash().get(stashId, Integer.class);
        Assert.assertEquals(actualResult, expectedResult);
    }

    @То("На странице {string} отображается элемент {string}")
    public void assertFieldIsDisplayed(String pageName, String fieldName) {
        WebElement element = CucumberPageObjectHelper.getElementBy(pageName, fieldName);
        Assert.assertTrue(
                BrowserUtils.isElementCurrentlyPresent(element)
        );
    }

    @То("На странице {string} не отображается элемент {string}")
    public void assertFieldIsNotDisplayed(String pageName, String fieldName) {
        WebElement element = CucumberPageObjectHelper.getElementBy(pageName, fieldName);
        Assert.assertFalse(
                BrowserUtils.isElementCurrentlyPresent(element)
        );
    }
}
