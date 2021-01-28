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
import redmine.model.dto.UserInfo;
import redmine.model.user.Language;
import redmine.model.user.User;
import redmine.utils.StringGenerators;
import redmine.utils.gson.GsonHelper;

public class TestCase2 {

    private ApiClient apiClient;

    @BeforeClass(description = "Подготовка данных: создание пользователя без админских прав. Создание API-подключения.")
    public void preparedFixtures() {
        User userNotAdmin = new User()
                .setAdmin(false)
                .setStatus(1)
                .setLanguage(Language.EN)
                .generate();
        apiClient = new RestApiClient(userNotAdmin);
    }

    @Test(description = "Создание нового пользователя через POST-запрос пользователем без админских прав")
    public void createUserWithoutAdminRights() {
        createNewUser();
    }

    @Step("Шаг 1. Создание нового пользователя через POST-запрос пользователем без админских прав")
    private void createNewUser() {
        String login = "Ser" + StringGenerators.randomString(8, StringGenerators.ENGLISH_LOWER);
        String firstName = "Nov" + StringGenerators.randomString(8, StringGenerators.ENGLISH);
        String lastName = StringGenerators.randomString(8, StringGenerators.ENGLISH);
        String mail = StringGenerators.randomEmail();
        String password = StringGenerators.randomString(8, StringGenerators.ENGLISH + StringGenerators.DIGITS_CHARACTERS);
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

        /*
          Проверка статус-кода.
         */

        Assert.assertEquals(response.getStatusCode(), 403);
    }
}
