package steps;

import cucumber.api.java.ru.И;
import cucumber.api.java.ru.То;
import org.openqa.selenium.WebElement;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.cucumber.ParametersValidator;
import redmine.managers.Context;
import redmine.model.dto.UserDto;
import redmine.model.user.User;
import redmine.ui.pages.helpers.CucumberPageObjectHelper;
import redmine.utils.Asserts;
import redmine.utils.BrowserUtils;

import java.time.temporal.ChronoUnit;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static redmine.model.dto.UserDto.readUserDtoById;
import static redmine.model.dto.UserDto.readUserDtoByLogin;

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
    public void assertDataBaseNewUser(String newUserStashId) {
        User userBeforeCreation = Context.get(newUserStashId, User.class);
        User userFromDb = userBeforeCreation.read();

        Asserts.assertNotNull(userFromDb.getId());
        Asserts.assertEquals(userBeforeCreation.getLogin(), userFromDb.getLogin());
        Asserts.assertEquals(userBeforeCreation.getFirstName(), userFromDb.getFirstName());
        Asserts.assertEquals(userBeforeCreation.getLastName(), userFromDb.getLastName());
        Asserts.assertEquals(userBeforeCreation.getEmail().getAddress(), userFromDb.getEmail().getAddress());
    }

    @И("В ответе {string} статус код ответа {string}")
    public void assertStatusCode(String responseStashId, String statusCode) {
        Response response = Context.get(responseStashId, Response.class);

        Asserts.assertEquals(response.getStatusCode(), Integer.parseInt(statusCode));
    }

    @И("Тело ответа {string} содержит данные пользователя {string}, в том числе его id")
    public void assertResponseUserData(String responseStashId, String userDtoStashId) {
        Response response = Context.get(responseStashId, Response.class);
        UserDto userForCreation = Context.get(userDtoStashId, UserDto.class);

        UserDto user = response.getBody(UserDto.class);

        Asserts.assertNotNull(user.getUser().getId());
        Asserts.assertEquals(user.getUser().getLogin(), userForCreation.getUser().getLogin());
        Asserts.assertEquals(user.getUser().getFirstname(), userForCreation.getUser().getFirstname());
        Asserts.assertEquals(user.getUser().getLastname(), userForCreation.getUser().getLastname());
        Asserts.assertEquals(user.getUser().getMail(), userForCreation.getUser().getMail());
        Asserts.assertNotNull(user.getUser().getCreated_on());
        Asserts.assertNull(user.getUser().getLast_login_on());
        Asserts.assertEquals(user.getUser().getStatus(), userForCreation.getUser().getStatus());
        Asserts.assertNotNull(user.getUser().getApi_key());
        Asserts.assertNull(user.getUser().getPassword());
    }

    @И("Информация в базе данных о созданном пользователе сходится с ответом {string}")
    public void assertDbUserData(String responseStashId) {
        Response response = Context.get(responseStashId, Response.class);
        UserDto userDto = response.getBody(UserDto.class);

        User userFromDb = readUserDtoByLogin(userDto.getUser().getLogin());

        Asserts.assertEquals(userDto.getUser().getId(), userFromDb.getId());
        Asserts.assertEquals(userDto.getUser().getLogin(), userFromDb.getLogin());
        Asserts.assertEquals(userDto.getUser().getAdmin(), userFromDb.getAdmin());
        Asserts.assertEquals(userDto.getUser().getFirstname(), userFromDb.getFirstName());
        Asserts.assertEquals(userDto.getUser().getLastname(), userFromDb.getLastName());
        Asserts.assertEquals(userDto.getUser().getMail(), userFromDb.getEmail().getAddress());
        Asserts.assertTrue(ChronoUnit.SECONDS.between(userDto.getUser().getCreated_on(), userFromDb.getCreatedOn()) <= 1);
        Asserts.assertNull(userFromDb.getLastLoginOn());
        Asserts.assertEquals(userDto.getUser().getStatus(), userFromDb.getStatus());
        Asserts.assertEquals(userDto.getUser().getApi_key(), userFromDb.getApiToken().getValue());
        Asserts.assertNotNull(userFromDb.getHashedPassword());
    }
}
