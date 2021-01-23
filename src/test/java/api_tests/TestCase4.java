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

public class TestCase4 {

    private ApiClient apiClient;
    private User user;
    private User anotherUser;

    @BeforeClass
    @Test(description = "Подготовка данных: генерация пользователя без админских прав в БД. Генерация второго пользователя в БД")
    public void preparedFixtures() {
        user = new User().setAdmin(false).setStatus(1).setLanguage(Language.EN).generate();
        anotherUser = new User().setAdmin(false).setStatus(1).setLanguage(Language.EN).generate();
    }

    @Test(description = "Шаг 1. Удаление второго пользователя через DELETE-запрос. Использование своего API-ключа первым пользователем", priority = 1)
    public void deleteAnotherUser() {
        String uri = String.format("users/%d.json", anotherUser.getId());

        apiClient = new RestApiClient(user);
        Request request = new RestRequest(uri, HttpMethods.DELETE, null, null, null);
        Response response = apiClient.executeRequest(request);

        /*
          Проверка статус-кода и присутствия данных пользователя в БД
         */

        Assert.assertEquals(response.getStatusCode(), 403);

        Assert.assertNotNull(user.read());
    }

    @Test(description = "Шаг 2. Удаление пользователя через DELETE-запрос. Использование вторым пользователем API-ключа первого пользователя", priority = 2)
    public void deleteUser() {
        String uri = String.format("users/%d.json", user.getId());

        apiClient = new RestApiClient(user);
        Request request = new RestRequest(uri, HttpMethods.DELETE, null, null, null);
        Response response = apiClient.executeRequest(request);

        /*
          Проверка статус-кода и присутствия данных пользователя в БД
         */

        Assert.assertEquals(response.getStatusCode(), 403);

        Assert.assertNotNull(user.read());
    }
}
