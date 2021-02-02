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
    public void prepareFixtures() {
        User userNotAdmin = new User()
                .setAdmin(false)
                .setStatus(1)
                .setLanguage(Language.EN)
                .generate();
        apiClient = new RestApiClient(userNotAdmin);
    }

    @Test(description = "Кейс 2. Создание нового пользователя через POST-запрос пользователем без админских прав")
    public void createUserWithoutAdminRights() {
        createNewUser();
    }

    @Step("Шаг 1. Создание нового пользователя через POST-запрос пользователем без админских прав")
    private void createNewUser() {
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

        Assert.assertEquals(response.getStatusCode(), 403);
    }
}
