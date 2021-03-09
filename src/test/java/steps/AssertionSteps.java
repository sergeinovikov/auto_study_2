package steps;

import cucumber.api.java.ru.И;
import cucumber.api.java.ru.То;
import org.openqa.selenium.WebElement;
import redmine.cucumber.ParametersValidator;
import redmine.managers.Context;
import redmine.model.user.User;
import redmine.ui.pages.helpers.CucumberPageObjectHelper;
import redmine.utils.Asserts;
import redmine.utils.BrowserUtils;

public class AssertionSteps {

    @То("Значение переменной {string} будет равно {int}")
    public void assertResult(String stashId, Integer expectedResult) {
        Integer actualResult = Context.get(stashId, Integer.class);
        Asserts.assertEquals(actualResult, expectedResult);
    }

    @То("На странице {string} отображается элемент {string}")
    public void assertFieldIsDisplayed(String pageName, String fieldName) {
        WebElement element = CucumberPageObjectHelper.getElementBy(pageName, fieldName);
        Asserts.assertTrue(
                BrowserUtils.isElementCurrentlyPresent(element)
        );
    }

    @То("На странице {string} не отображается элемент {string}")
    public void assertFieldIsNotDisplayed(String pageName, String fieldName) {
        WebElement element = CucumberPageObjectHelper.getElementBy(pageName, fieldName);
        Asserts.assertFalse(
                BrowserUtils.isElementCurrentlyPresent(element)
        );
    }

    @То("На странице {string} отображается элемент {string} с текстом {string}")
    public void assertFieldTextIsDisplayed(String pageName, String fieldName, String rawString) {
        String actualFieldText = ParametersValidator.replaceCucumberVariables(rawString);
        WebElement element = CucumberPageObjectHelper.getElementBy(pageName, fieldName);
        Asserts.assertTrue(
                BrowserUtils.isElementCurrentlyPresent(element)
        );
        Asserts.assertEquals(element.getText(), actualFieldText);
    }

    @И("В базе данных в таблице users появилась запись с данными пользователя {string}")
    public void dataBaseNewUserCheck(String newUserStashId) {
        User userBeforeCreation = Context.get(newUserStashId, User.class);
        User userFromDb = userBeforeCreation.read();

        Asserts.assertNotNull(userFromDb.getId());
        Asserts.assertEquals(userBeforeCreation.getLogin(), userFromDb.getLogin());
        Asserts.assertEquals(userBeforeCreation.getFirstName(), userFromDb.getFirstName());
        Asserts.assertEquals(userBeforeCreation.getLastName(), userFromDb.getLastName());
        Asserts.assertEquals(userBeforeCreation.getEmail().getAddress(), userFromDb.getEmail().getAddress());
    }
}
