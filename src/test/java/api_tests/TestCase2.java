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
import redmine.model.user.Language;
import redmine.model.user.User;
import redmine.utils.StringGenerators;

public class TestCase2 {

    private ApiClient apiClient;
    private User user;

    @BeforeClass
    @Test(description = "Подготовка данных: создание польователя без админских прав. Создание API-подключения. Создание нового пользователя")
    public void preparedFixtures() {
        User userWithApiKey = new User().setAdmin(false).setStatus(1).setLanguage(Language.EN).generate();
        apiClient = new RestApiClient(userWithApiKey);
        user = new User().setStatus(2);
    }

    @Test(description = "Шаг 1. Создание нового пользователя через POST-запрос пользователем без админских прав", priority = 1)
    public void createNewUser() {
        String password = StringGenerators.randomString(8, StringGenerators.ENGLISH + StringGenerators.DIGITS_CHARACTERS);

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
          Проверка статус-кода.
         */

        Assert.assertEquals(response.getStatusCode(), 403);
    }
}
