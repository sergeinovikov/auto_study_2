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
import redmine.model.dto.UserDto;
import redmine.model.user.Language;
import redmine.model.user.User;

public class TestCase3 {

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

    @Test(description = "Кейс 3. Получение пользователей. Пользователь без прав администратора")
    public void getUsersWithoutAdminRights() {
        getUser(firstUser);
        getUserWithAnotherApiKey(firstUser, secondUser);
    }

    @Step("Шаг 1. Получение первого пользователя через GET-запрос используя API-ключ первого пользователя")
    private void getUser(User user) {
        String uri = String.format("users/%d.json", user.getId());

        apiClient = new RestApiClient(user);
        Request request = new RestRequest(uri, HttpMethods.GET, null, null, null);
        Response response = apiClient.executeRequest(request);

        /*
          Проверка статус-кода и данных пользователя, указанных при его генерации в БД
         */

        Assert.assertEquals(response.getStatusCode(), 200);

        UserDto getApiUser = response.getBody(UserDto.class);

        Assert.assertEquals(getApiUser.getUser().getAdmin(), user.getAdmin());
        Assert.assertEquals(getApiUser.getUser().getApi_key(), user.getApiToken().getValue());
    }

    @Step("Шаг 2. Получение второго пользователя через GET-запрос используя API-ключ первого пользователя")
    public void getUserWithAnotherApiKey(User user, User anotherUser) {
        String uri = String.format("users/%d.json", anotherUser.getId());

        apiClient = new RestApiClient(user);
        Request request = new RestRequest(uri, HttpMethods.GET, null, null, null);
        Response response = apiClient.executeRequest(request);

        /*
          Проверка статус-кода и отсутствие доступа к данным другого пользователя
         */

        Assert.assertEquals(response.getStatusCode(), 200);

        UserDto getApiUser = response.getBody(UserDto.class);

        Assert.assertNull(getApiUser.getUser().getAdmin());
        Assert.assertNull(getApiUser.getUser().getApi_key());
    }
}
