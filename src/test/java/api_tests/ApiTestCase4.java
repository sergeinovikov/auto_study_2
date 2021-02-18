package api_tests;

import io.qameta.allure.Step;
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

public class ApiTestCase4 {

    private ApiClient apiClient;
    private User firstUser;
    private User secondUser;

    @BeforeClass(description = "Подготовка данных: генерация пользователя без админских прав в БД. Генерация второго пользователя в БД")
    public void prepareFixtures() {
        firstUser = new User()
                .setAdmin(false)
                .setStatus(1)
                .setLanguage(Language.EN)
                .generate();
        secondUser = new User()
                .setAdmin(false)
                .setStatus(1)
                .setLanguage(Language.EN)
                .generate();
    }

    @Test(description = "Кейс 4. Удаление пользователей. Пользователь без прав администратора")
    public void deleteUsersWithoutAdminRights() {
        deleteAnotherUser(firstUser, secondUser);
        deleteUser(firstUser);
    }

    @Step("Шаг 1. Удаление второго {0} через DELETE-запрос используя API-ключ первого {1}")
    private void deleteAnotherUser(User user, User anotherUser) {
        String uri = String.format("users/%d.json", anotherUser.getId());

        apiClient = new RestApiClient(user);
        Request request = new RestRequest(uri, HttpMethods.DELETE, null, null, null);
        Response response = apiClient.executeRequest(request);

        Assert.assertEquals(response.getStatusCode(), 403);

        Assert.assertNotNull(user.read());
    }

    @Step("Шаг 2. Удаление первого пользователя через DELETE-запрос используя API-ключ первого пользователя")
    private void deleteUser(User user) {
        String uri = String.format("users/%d.json", user.getId());

        apiClient = new RestApiClient(user);
        Request request = new RestRequest(uri, HttpMethods.DELETE, null, null, null);
        Response response = apiClient.executeRequest(request);

        Assert.assertEquals(response.getStatusCode(), 403);

        Assert.assertNotNull(user.read());
    }
}
