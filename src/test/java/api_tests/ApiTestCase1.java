package api_tests;

import io.qameta.allure.Step;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.HttpMethods;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.model.dto.UserCreationError;
import redmine.model.dto.UserDto;
import redmine.model.dto.UserInfo;
import redmine.model.user.Language;
import redmine.model.user.User;
import redmine.utils.Asserts;
import redmine.utils.StringGenerators;
import redmine.utils.gson.GsonHelper;

import java.time.temporal.ChronoUnit;

import static redmine.model.dto.UserDto.readUserDtoById;

public class ApiTestCase1 {

    private ApiClient apiClient;

    @BeforeClass(description = "Подготовка данных: создание пользователя с админскими правами. Создание API-подключения.")
    public void prepareFixtures() {
        User userWithApiKey = new User()
                .setAdmin(true)
                .setStatus(1)
                .setLanguage(Language.EN);
        userWithApiKey.generate();
        apiClient = new RestApiClient(userWithApiKey);
    }

    @Test(description = "Кейс 1. Создание, изменение, получение, удаление пользователя. Администратор системы")
    public void testCase1() {
        UserDto user = createNewUser();
        createUserWithSameData(user);
        createUserWithInvalidData(user);
        updateUser(user);
        getUser(user);
        deleteUser(user);
        deleteSameUser(user);
    }

    @Step("Шаг 1. Создание пользователя через API и проверка данных в БД")
    private UserDto createNewUser() {
        String login = "SN" + StringGenerators.randomEnglishLowerString(8);
        String firstName = "Ser" + StringGenerators.randomEnglishString(8);
        String lastName = "Nov" + StringGenerators.randomEnglishString(8);
        String mail = StringGenerators.randomEmail(8);
        String password = StringGenerators.randomPassword(8);
        Integer status = 2;

        UserDto userForCreation = new UserDto()
                .setUser(new UserInfo()
                        .setLogin(login)
                        .setFirstname(firstName)
                        .setLastname(lastName)
                        .setMail(mail)
                        .setPassword(password)
                        .setStatus(status)
                );
        String body = GsonHelper.getGson().toJson(userForCreation);

        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);

        Asserts.assertEquals(response.getStatusCode(), 201);

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

        User userFromDb = readUserDtoById(user.getUser().getId());

        Asserts.assertEquals(user.getUser().getId(), userFromDb.getId());
        Asserts.assertEquals(user.getUser().getLogin(), userFromDb.getLogin());
        Asserts.assertEquals(user.getUser().getAdmin(), userFromDb.getAdmin());
        Asserts.assertEquals(user.getUser().getFirstname(), userFromDb.getFirstName());
        Asserts.assertEquals(user.getUser().getLastname(), userFromDb.getLastName());
        Asserts.assertEquals(user.getUser().getMail(), userFromDb.getEmail().getAddress());
        Asserts.assertTrue(ChronoUnit.SECONDS.between(user.getUser().getCreated_on(), userFromDb.getCreatedOn()) <= 1);
        Asserts.assertNull(userFromDb.getLastLoginOn());
        Asserts.assertEquals(user.getUser().getStatus(), userFromDb.getStatus());
        Asserts.assertEquals(user.getUser().getApi_key(), userFromDb.getApiToken().getValue());
        Asserts.assertNotNull(userFromDb.getHashedPassword());

        return user;
    }


    @Step("Шаг 2. Создание пользователя через POST-запрос повторно с тем же телом запроса")
    private void createUserWithSameData(UserDto user) {
        String body = GsonHelper.getGson().toJson(user);

        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);

        Asserts.assertEquals(response.getStatusCode(), 422);

        UserCreationError errors = response.getBody(UserCreationError.class);

        Asserts.assertEquals(errors.getErrors().size(), 2);
        Asserts.assertEquals(errors.getErrors().get(0), "Email has already been taken");
        Asserts.assertEquals(errors.getErrors().get(1), "Login has already been taken");
    }

    @Step("Шаг 3. Создание пользователя через POST-запрос повторно с тем же телом запроса, но с невалидными email и password")
    private void createUserWithInvalidData(UserDto user) {
        String login = user.getUser().getLogin();
        String firstName = user.getUser().getFirstname();
        String lastName = user.getUser().getLastname();
        Integer status = user.getUser().getStatus();
        String invalidEMail = "invalid_email";
        String invalidPassword = "123";

        UserDto userWithInvalidData = new UserDto()
                .setUser(new UserInfo()
                        .setLogin(login)
                        .setFirstname(firstName)
                        .setLastname(lastName)
                        .setMail(invalidEMail)
                        .setPassword(invalidPassword)
                        .setStatus(status)
                );

        String body = GsonHelper.getGson().toJson(userWithInvalidData);

        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);


        Asserts.assertEquals(response.getStatusCode(), 422);

        UserCreationError errors = response.getBody(UserCreationError.class);

        Asserts.assertEquals(errors.getErrors().size(), 3);
        Asserts.assertEquals(errors.getErrors().get(0), "Email is invalid");
        Asserts.assertEquals(errors.getErrors().get(1), "Login has already been taken");
        Asserts.assertEquals(errors.getErrors().get(2), "Password is too short (minimum is 8 characters)");
    }

    @Step("Шаг 4. Изменение пользователя через PUT-запрос c данными ответа по запросу из шага 1, при этом изменив поле status = 1")
    private void updateUser(UserDto user) {
        String uri = String.format("users/%d.json", user.getUser().getId());
        Integer newStatus = 1;

        user.getUser().setStatus(newStatus);

        String body = GsonHelper.getGson().toJson(user);

        Request request = new RestRequest(uri, HttpMethods.PUT, null, null, body);
        Response response = apiClient.executeRequest(request);

        Asserts.assertEquals(response.getStatusCode(), 204);

        User userFromDb = readUserDtoById(user.getUser().getId());

        Asserts.assertEquals(userFromDb.getStatus(), newStatus);
    }

    @Step("Шаг 5. Получение пользователя через GET-запрос")
    private void getUser(UserDto user) {
        String uri = String.format("users/%d.json", user.getUser().getId());

        Request request = new RestRequest(uri, HttpMethods.GET, null, null, null);
        Response response = apiClient.executeRequest(request);

        Asserts.assertEquals(response.getStatusCode(), 200);

        user = response.getBody(UserDto.class);

        Asserts.assertEquals(user.getUser().getLogin(), user.getUser().getLogin());
        Asserts.assertEquals(user.getUser().getFirstname(), user.getUser().getFirstname());
        Asserts.assertEquals(user.getUser().getLastname(), user.getUser().getLastname());
        Asserts.assertEquals(user.getUser().getMail(), user.getUser().getMail());
        Asserts.assertNull(user.getUser().getPassword());
        Asserts.assertEquals(user.getUser().getStatus(), user.getUser().getStatus());
    }

    @Step("Шаг 6. Удаление пользователя через DELETE-запрос")
    private void deleteUser(UserDto user) {
        String uri = String.format("users/%d.json", user.getUser().getId());

        Request request = new RestRequest(uri, HttpMethods.DELETE, null, null, null);
        Response response = apiClient.executeRequest(request);

        Asserts.assertEquals(response.getStatusCode(), 204);

        User deletedUser = readUserDtoById(user.getUser().getId());

        Asserts.assertNull(deletedUser);
    }

    @Step("Шаг 7. Повторно удаление пользователя через DELETE-запрос")
    private void deleteSameUser(UserDto user) {
        String uri = String.format("users/%d.json", user.getUser().getId());

        Request request = new RestRequest(uri, HttpMethods.DELETE, null, null, null);
        Response response = apiClient.executeRequest(request);

        Asserts.assertEquals(response.getStatusCode(), 404);

        User deletedUser = readUserDtoById(user.getUser().getId());

        Asserts.assertNull(deletedUser);
    }
}
