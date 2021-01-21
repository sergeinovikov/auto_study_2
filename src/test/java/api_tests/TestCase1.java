package api_tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.HttpMethods;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.model.dto.UserDto;
import redmine.model.user.User;
import redmine.utils.StringGenerators;

public class TestCase1 {

    private ApiClient apiClient;

    @BeforeMethod
    @Test(description = "Подготовка данных: создани польователя с админскими правами. Создание API-подключения")
    public void preparedFixtures() {
        User userWithApiKey = new User().setAdmin(true).setStatus(1).generate();
        apiClient = new RestApiClient(userWithApiKey);
    }

    @Test(description = "Шаг 1. Создание пользователя через POST-запрос")
    public void createUser() {
        User user = new User();
        String password = StringGenerators.randomString(8, StringGenerators.ENGLISH + StringGenerators.DIGITS_CHARACTERS);

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
          Проверка статус-кода, отправленных и полученных данных пользователя, а также наличие пользоваеля в БД.
         */

        Assert.assertEquals(response.getStatusCode(), 201);

        UserDto createdApiUser = response.getBody(UserDto.class);

        Assert.assertNotNull(createdApiUser.getUser().getId());
        Assert.assertEquals(createdApiUser.getUser().getLogin(), user.getLogin());
        Assert.assertEquals(createdApiUser.getUser().getFirstname(), user.getFirstName());
        Assert.assertEquals(createdApiUser.getUser().getLastname(), user.getLastName());
        Assert.assertEquals(createdApiUser.getUser().getMail(), user.getEmail().getAddress());
        Assert.assertNull(createdApiUser.getUser().getPassword());

        User userFromDb = new User();
        userFromDb.setId(createdApiUser.getUser().getId());
        User user1 = userFromDb.read();

        Assert.assertEquals(createdApiUser.getUser().getLogin(), user1.getLogin());
        Assert.assertEquals(createdApiUser.getUser().getFirstname(), user1.getFirstName());
        Assert.assertEquals(createdApiUser.getUser().getLastname(), user1.getLastName());
        Assert.assertEquals(createdApiUser.getUser().getMail(), user1.getEmail().getAddress());
        Assert.assertNotNull(user1.getHashedPassword());
    }
}
