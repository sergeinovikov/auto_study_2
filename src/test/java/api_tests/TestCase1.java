package api_tests;

import org.testng.Assert;
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
import redmine.model.user.Language;
import redmine.model.user.User;
import redmine.utils.StringGenerators;

public class TestCase1 {

    private ApiClient apiClient;
    private User user;
    private String password;
    private UserDto createdApiUser;
    private String uri;
    private Integer newStatus;

    @BeforeClass
    @Test(description = "Подготовка данных: создание пользователя с админскими правами. Создание API-подключения. Генерация пользователя в БД. Генерация пароля для пользователя")
    public void preparedFixtures() {
        User userWithApiKey = new User().setAdmin(true).setStatus(1).setLanguage(Language.EN).generate();
        apiClient = new RestApiClient(userWithApiKey);
        user = new User().setStatus(2);
        password = StringGenerators.randomString(8, StringGenerators.ENGLISH + StringGenerators.DIGITS_CHARACTERS);
    }

    @Test(description = "Шаг 1. Создание нового пользователя через POST-запрос", priority = 1)
    public void createNewUser() {
        String body = String.format("{\n" +
                "    \"user\": {\n" +
                "        \"login\": \"%s\",\n" +
                "        \"firstname\": \"%s\",\n" +
                "        \"lastname\": \"%s\",\n" +
                "        \"mail\": \"%s\",\n" +
                "        \"password\": \"%s\",\n" +
                "        \"status\": \"%s\" \n" +
                "    }\n" +
                "}", user.getLogin(), user.getFirstName(), user.getLastName(), user.getEmail().getAddress(), password, user.getStatus());

        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);

        /*
          Проверка статус-кода, отправленных и полученных данных пользователя, а также наличие пользоваеля в БД.
         */

        Assert.assertEquals(response.getStatusCode(), 201);

        createdApiUser = response.getBody(UserDto.class);

        Assert.assertNotNull(createdApiUser.getUser().getId());
        Assert.assertEquals(createdApiUser.getUser().getLogin(), user.getLogin());
        Assert.assertEquals(createdApiUser.getUser().getAdmin(), user.getAdmin());
        Assert.assertEquals(createdApiUser.getUser().getFirstname(), user.getFirstName());
        Assert.assertEquals(createdApiUser.getUser().getLastname(), user.getLastName());
        Assert.assertEquals(createdApiUser.getUser().getMail(), user.getEmail().getAddress());
        Assert.assertNotNull(createdApiUser.getUser().getCreated_on());
        Assert.assertNull(createdApiUser.getUser().getLast_login_on());
        Assert.assertEquals(createdApiUser.getUser().getStatus(), user.getStatus());
        Assert.assertNotNull(createdApiUser.getUser().getApi_key());
        Assert.assertNull(createdApiUser.getUser().getPassword());

        User userFromDb = new User().setId(createdApiUser.getUser().getId()).read();

        Assert.assertEquals(createdApiUser.getUser().getId(), userFromDb.getId());
        Assert.assertEquals(createdApiUser.getUser().getLogin(), userFromDb.getLogin());
        Assert.assertEquals(createdApiUser.getUser().getAdmin(), userFromDb.getAdmin());
        Assert.assertEquals(createdApiUser.getUser().getFirstname(), userFromDb.getFirstName());
        Assert.assertEquals(createdApiUser.getUser().getLastname(), userFromDb.getLastName());
        Assert.assertEquals(createdApiUser.getUser().getMail(), userFromDb.getEmail().getAddress());
        //Assert.assertEquals(createdApiUser.getUser().getCreated_on(), DateFormatter.convertDate(userFromDb.getCreatedOn()));
        Assert.assertNull(userFromDb.getLastLoginOn());
        Assert.assertEquals(createdApiUser.getUser().getStatus(), userFromDb.getStatus());
        Assert.assertNotNull(createdApiUser.getUser().getApi_key(), userFromDb.getApiToken().getValue());
        Assert.assertNotNull(userFromDb.getHashedPassword());
    }

    @Test(description = "Шаг 2. Создание пользователя через POST-запрос повторно с тем же телом запроса", priority = 2)
    public void createUserWithSameData() {
        String body = String.format("{\n" +
                "    \"user\": {\n" +
                "        \"login\": \"%s\",\n" +
                "        \"firstname\": \"%s\",\n" +
                "        \"lastname\": \"%s\",\n" +
                "        \"mail\": \"%s\",\n" +
                "        \"password\": \"%s\" \n" +
                "    }\n" +
                "}", user.getLogin(), user.getFirstName(), user.getLastName(), user.getEmail().getAddress(), password);

        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);

        /*
          Проверка статус-кода, а также сравнение текстов ошибок.
         */

        Assert.assertEquals(response.getStatusCode(), 422);

        UserCreationError errors = response.getBody(UserCreationError.class);

        Assert.assertEquals(errors.getErrors().size(), 2);
        Assert.assertEquals(errors.getErrors().get(0), "Email has already been taken");
        Assert.assertEquals(errors.getErrors().get(1), "Login has already been taken");
    }

    @Test(description = "Шаг 3. Создание пользователя через POST-запрос повторно с тем же телом запроса, но с невалидными email и password", priority = 3)
    public void createUserWithInvalidData() {
        String invalidEmail = "email.ru";
        String invalidPassword = "1234";

        String body = String.format("{\n" +
                "    \"user\": {\n" +
                "        \"login\": \"%s\",\n" +
                "        \"firstname\": \"%s\",\n" +
                "        \"lastname\": \"%s\",\n" +
                "        \"mail\": \"%s\",\n" +
                "        \"password\": \"%s\" \n" +
                "    }\n" +
                "}", user.getLogin(), user.getFirstName(), user.getLastName(), invalidEmail, invalidPassword);

        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);

        /*
          Проверка статус-кода, а также сравнение текстов ошибок.
         */

        Assert.assertEquals(response.getStatusCode(), 422);

        UserCreationError errors = response.getBody(UserCreationError.class);

        Assert.assertEquals(errors.getErrors().size(), 3);
        Assert.assertEquals(errors.getErrors().get(0), "Email is invalid");
        Assert.assertEquals(errors.getErrors().get(1), "Login has already been taken");
        Assert.assertEquals(errors.getErrors().get(2), "Password is too short (minimum is 8 characters)");
    }

    @Test(description = "Шаг 4. Изменение пользователя через PUT-запрос c данными ответа по запросу из шага 1, при этом изменив поле status = 1", priority = 4)
    public void updateUser() {
        uri = String.format("users/%d.json", createdApiUser.getUser().getId());
        newStatus = 1;

        String body = String.format("{\n" +
                "    \"user\": {\n" +
                "        \"login\": \"%s\",\n" +
                "        \"firstname\": \"%s\",\n" +
                "        \"lastname\": \"%s\",\n" +
                "        \"mail\": \"%s\",\n" +
                "        \"status\": \"%d\" \n" +
                "    }\n" +
                "}", createdApiUser.getUser().getLogin(), createdApiUser.getUser().getFirstname(), createdApiUser.getUser().getLastname(), createdApiUser.getUser().getMail(), newStatus);

        Request request = new RestRequest(uri, HttpMethods.PUT, null, null, body);
        Response response = apiClient.executeRequest(request);

        /*
          Проверка статус-кода и изменённых данных пользователя в БД
         */

        Assert.assertEquals(response.getStatusCode(), 204);

        User userFromDb = new User().setLogin(createdApiUser.getUser().getLogin()).read();

        Assert.assertEquals(userFromDb.getStatus(), newStatus);
    }

    @Test(description = "Шаг 5. Получение пользователя через GET-запрос", priority = 5)
    public void getUser() {
        Request request = new RestRequest(uri, HttpMethods.GET, null, null, null);
        Response response = apiClient.executeRequest(request);

        /*
          Проверка статус-кода и данных пользователя, указанных при его создании, включая изменённый статус
         */

        Assert.assertEquals(response.getStatusCode(), 200);

        UserDto getApiUser = response.getBody(UserDto.class);

        Assert.assertEquals(getApiUser.getUser().getLogin(), user.getLogin());
        Assert.assertEquals(getApiUser.getUser().getFirstname(), user.getFirstName());
        Assert.assertEquals(getApiUser.getUser().getLastname(), user.getLastName());
        Assert.assertEquals(getApiUser.getUser().getMail(), user.getEmail().getAddress());
        Assert.assertNull(getApiUser.getUser().getPassword());
        Assert.assertEquals(getApiUser.getUser().getStatus(), newStatus);
    }

    @Test(description = "Шаг 6. Удаление пользователя через DELETE-запрос", priority = 6)
    public void deleteUser() {
        Request request = new RestRequest(uri, HttpMethods.DELETE, null, null, null);
        Response response = apiClient.executeRequest(request);

        /*
          Проверка статус-кода и отсутствия данных пользователя в БД
         */

        Assert.assertEquals(response.getStatusCode(), 204);

        Assert.assertNull(user.read());
    }

    @Test(description = "Шаг 7. Повторно удаление пользователя через DELETE-запрос", priority = 7)
    public void deleteSameUser() {
        Request request = new RestRequest(uri, HttpMethods.DELETE, null, null, null);
        Response response = apiClient.executeRequest(request);

        /*
          Проверка статус-кода и отсутствия данных пользователя в БД
         */

        Assert.assertEquals(response.getStatusCode(), 404);

        Assert.assertNull(user.read());
    }
}
