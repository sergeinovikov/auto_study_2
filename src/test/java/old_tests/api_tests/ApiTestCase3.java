package old_tests.api_tests;

import io.qameta.allure.Step;
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
import redmine.utils.Asserts;

public class ApiTestCase3 {

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

        Asserts.assertEquals(response.getStatusCode(), 200);

        UserDto getApiUser = response.getBody(UserDto.class);

        Asserts.assertEquals(getApiUser.getUser().getAdmin(), user.getAdmin());
        Asserts.assertEquals(getApiUser.getUser().getApi_key(), user.getApiToken().getValue());
    }

    @Step("Шаг 2. Получение второго пользователя через GET-запрос используя API-ключ первого пользователя")
    public void getUserWithAnotherApiKey(User user, User anotherUser) {
        String uri = String.format("users/%d.json", anotherUser.getId());

        apiClient = new RestApiClient(user);
        Request request = new RestRequest(uri, HttpMethods.GET, null, null, null);
        Response response = apiClient.executeRequest(request);

        Asserts.assertEquals(response.getStatusCode(), 200);

        UserDto getApiUser = response.getBody(UserDto.class);

        Asserts.assertNull(getApiUser.getUser().getAdmin());
        Asserts.assertNull(getApiUser.getUser().getApi_key());
    }
}
