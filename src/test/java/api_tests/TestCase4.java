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

public class TestCase4 {

    private ApiClient apiClient;
    private User firstUser;
    private User secondUser;

    @BeforeClass(description = "Подготовка данных: генерация пользователя без админских прав в БД. Генерация второго пользователя в БД")
    public void preparedFixtures() {
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

    @Test(description = "Удаление пользователей. Пользователь без прав администратора")
    public void deleteUsersWithoutAdminRights() {
        deleteAnotherUser(firstUser, secondUser);
        deleteUser(firstUser, secondUser);
    }

    @Step("Шаг 1. Удаление второго пользователя через DELETE-запрос. Использование своего API-ключа первым пользователем")
    private void deleteAnotherUser(User user, User anotherUser) {
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

    @Step("Шаг 2. Удаление пользователя через DELETE-запрос. Использование вторым пользователем API-ключа первого пользователя")
    private void deleteUser(User user, User anotherUser) {
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
