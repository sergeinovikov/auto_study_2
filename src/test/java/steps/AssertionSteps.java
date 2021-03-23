package steps;

import cucumber.api.java.ru.И;
import cucumber.api.java.ru.То;
import org.openqa.selenium.WebElement;
import redmine.api.interfaces.Response;
import redmine.cucumber.ParametersValidator;
import redmine.managers.Context;
import redmine.model.dto.UserCreationError;
import redmine.model.dto.UserDto;
import redmine.model.user.User;
import redmine.ui.pages.helpers.CucumberPageObjectHelper;
import redmine.utils.Asserts;
import redmine.utils.BrowserUtils;

import java.time.temporal.ChronoUnit;
import java.util.List;

import static redmine.model.dto.UserDto.readUserDtoById;

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

    @И("Тело ответа {string} содержит данные пользователя {string}")
    public void assertResponseUserData(String responseStashId, String userDtoStashId) {
        Response response = Context.get(responseStashId, Response.class);
        UserDto userForCreation = Context.get(userDtoStashId, UserDto.class);

        UserDto userFromResponse = response.getBody(UserDto.class);

        Asserts.assertNotNull(userFromResponse.getUser().getId());
        Asserts.assertEquals(userFromResponse.getUser().getLogin(), userForCreation.getUser().getLogin());
        Asserts.assertEquals(userFromResponse.getUser().getFirstname(), userForCreation.getUser().getFirstname());
        Asserts.assertEquals(userFromResponse.getUser().getLastname(), userForCreation.getUser().getLastname());
        Asserts.assertEquals(userFromResponse.getUser().getMail(), userForCreation.getUser().getMail());
        Asserts.assertNotNull(userFromResponse.getUser().getCreated_on());
        Asserts.assertNull(userFromResponse.getUser().getLast_login_on());
        Asserts.assertEquals(userFromResponse.getUser().getStatus(), userForCreation.getUser().getStatus());
        Asserts.assertNotNull(userFromResponse.getUser().getApi_key());
        Asserts.assertNull(userFromResponse.getUser().getPassword());
    }

    @И("Информация в базе данных о созданном пользователе сходится с ответом {string}")
    public void assertDbUserData(String responseStashId) {
        Response response = Context.get(responseStashId, Response.class);
        UserDto userDto = response.getBody(UserDto.class);

        User userFromDb = readUserDtoById(userDto.getUser().getId());

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

    @И("В ответе {string} присутствуют ошибки:")
    public void assertApiErrors(String responseStashId, List<String> errorsList) {
        Response response = Context.get(responseStashId, Response.class);
        UserCreationError actualErrors = response.getBody(UserCreationError.class);

        UserCreationError expectedErrors = new UserCreationError(errorsList);

        Asserts.assertEquals(actualErrors, expectedErrors);
    }

    @И("Информация в базе данных об пользователе {string} присутствует, статус = {int}")
    public void assertModifiedDbUserData(String userDtoStashId, int statusCode) {
        UserDto userDto = Context.get(userDtoStashId, UserDto.class);

        User userFromDb = readUserDtoById(userDto.getUser().getId());

        Asserts.assertEquals(userDto.getUser().getId(), userFromDb.getId());
        Asserts.assertEquals(userDto.getUser().getLogin(), userFromDb.getLogin());
        Asserts.assertEquals(userDto.getUser().getAdmin(), userFromDb.getAdmin());
        Asserts.assertEquals(userDto.getUser().getFirstname(), userFromDb.getFirstName());
        Asserts.assertEquals(userDto.getUser().getLastname(), userFromDb.getLastName());
        Asserts.assertEquals(userDto.getUser().getMail(), userFromDb.getEmail().getAddress());
        Asserts.assertTrue(ChronoUnit.SECONDS.between(userDto.getUser().getCreated_on(), userFromDb.getCreatedOn()) <= 1);
        Asserts.assertNull(userFromDb.getLastLoginOn());
        Asserts.assertEquals(statusCode, userFromDb.getStatus());
        Asserts.assertEquals(userDto.getUser().getApi_key(), userFromDb.getApiToken().getValue());
        Asserts.assertNotNull(userFromDb.getHashedPassword());
    }

    @И("Информация в базе данных об удалённом пользователе {string} отсутствует")
    public void assertUsrDeletedFromDb(String userDtoStashId) {
        UserDto userDto = Context.get(userDtoStashId, UserDto.class);

        User deletedUser = readUserDtoById(userDto.getUser().getId());
        Asserts.assertNull(deletedUser);
    }

    @И("В теле ответа {string} присутствует информация {string}, включая поля поля admin: false, api_key")
    public void assertResponseUserDataWithApiKey (String responseStashId, String userStashId) {
        Response response = Context.get(responseStashId, Response.class);
        User user = Context.get(userStashId, User.class);

        UserDto userFromResponse = response.getBody(UserDto.class);

        Asserts.assertEquals(userFromResponse.getUser().getId(), user.getId());
        Asserts.assertEquals(userFromResponse.getUser().getLogin(), user.getLogin());
        Asserts.assertEquals(userFromResponse.getUser().getFirstname(), user.getFirstName());
        Asserts.assertEquals(userFromResponse.getUser().getLastname(), user.getLastName());
        Asserts.assertTrue(ChronoUnit.SECONDS.between(userFromResponse.getUser().getCreated_on(), user.getCreatedOn()) <= 1);
        Asserts.assertEquals(userFromResponse.getUser().getLast_login_on(), user.getLastLoginOn());

        Asserts.assertEquals(userFromResponse.getUser().getAdmin(), user.getAdmin());
        Asserts.assertEquals(userFromResponse.getUser().getApi_key(), user.getApiToken().getValue());
    }

    @И("В теле ответа {string} присутствует информация пользователя {string}, но отсутствуют поля admin: false, api_key")
    public void assertResponseUserDataWithoutApiKey (String responseStashId, String userStashId) {
        Response response = Context.get(responseStashId, Response.class);
        User user = Context.get(userStashId, User.class);

        UserDto userFromResponse = response.getBody(UserDto.class);

        Asserts.assertEquals(userFromResponse.getUser().getId(), user.getId());
        Asserts.assertEquals(userFromResponse.getUser().getLogin(), user.getLogin());
        Asserts.assertEquals(userFromResponse.getUser().getFirstname(), user.getFirstName());
        Asserts.assertEquals(userFromResponse.getUser().getLastname(), user.getLastName());
        Asserts.assertTrue(ChronoUnit.SECONDS.between(userFromResponse.getUser().getCreated_on(), user.getCreatedOn()) <= 1);
        Asserts.assertEquals(userFromResponse.getUser().getLast_login_on(), user.getLastLoginOn());

        Asserts.assertNull(userFromResponse.getUser().getAdmin());
        Asserts.assertNull(userFromResponse.getUser().getApi_key());
    }

    @И("Информация в базе данных об пользователе {string} присутствует")
    public void assertDbUserDataExist(String userStashId) {
        User user = Context.get(userStashId, User.class);

        Asserts.assertNotNull(user.read());
    }
}
