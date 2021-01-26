package rest_tests;

import org.testng.annotations.Test;
import org.testng.Assert;
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


public class MyFirstRestTests {

    @Test
    public void restRequestTest() {
        User admin = new User();
        admin.setAdmin(true);
        admin.setStatus(1);
        admin.setLanguage(Language.RU);
        admin.generate();

        ApiClient apiClient = new RestApiClient(admin);
        Request request = new RestRequest("roles.json", HttpMethods.GET, null, null, null);
        Response response = apiClient.executeRequest(request);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void createUserTest() {
        User admin = new User();
        admin.setAdmin(true);
        admin.setStatus(1);
        admin.generate();

        User user = new User();
        user.setStatus(1);

        String body = String.format("{\n" +
                "    \"user\": {\n" +
                "        \"login\": \"%s\",\n" +
                "        \"firstname\": \"%s\",\n" +
                "        \"lastname\": \"%s\",\n" +
                "        \"mail\": \"%s\",\n" +
                "        \"password\": \"1qazd@sS\" \n" +
                "    }\n" +
                "}", user.getLogin(), user.getFirstName(), user.getLastName(), user.getEmail().getAddress());
        ApiClient apiClient = new RestApiClient(admin);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);

        Assert.assertEquals(response.getStatusCode(), 201);

        UserDto createdUser = response.getBody(UserDto.class);

        Assert.assertEquals(createdUser.getUser().getLogin(), user.getLogin());
        Assert.assertEquals(createdUser.getUser().getFirstname(), user.getFirstName());
        Assert.assertEquals(createdUser.getUser().getLastname(), user.getLastName());
        Assert.assertNull(createdUser.getUser().getPassword());
        Assert.assertEquals(createdUser.getUser().getMail(), user.getEmail().getAddress());
        Assert.assertNull(createdUser.getUser().getLast_login_on());
        Assert.assertNotNull(createdUser.getUser().getCreated_on());
        Assert.assertEquals(createdUser.getUser().getStatus(), user.getStatus());
        Assert.assertFalse(createdUser.getUser().getAdmin());
    }

    @Test
    public void createUserWithInvalidPassword() {
        User admin = new User();
        admin.setAdmin(true);
        admin.setStatus(1);
        admin.setLanguage(Language.EN);
        admin.generate();

        User user = new User();
        String password = "123456";

        String body = String.format("{\n" +
                "    \"user\": {\n" +
                "        \"login\": \"%s\",\n" +
                "        \"firstname\": \"%s\",\n" +
                "        \"lastname\": \"%s\",\n" +
                "        \"mail\": \"%s\",\n" +
                "        \"password\": \"%s\" \n" +
                "    }\n" +
                "}", user.getLogin(), user.getFirstName(), user.getLastName(), user.getEmail().getAddress(), password);
        ApiClient apiClient = new RestApiClient(admin);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);

        Assert.assertEquals(response.getStatusCode(), 422);

        UserCreationError errors = response.getBody(UserCreationError.class);

        Assert.assertEquals(errors.getErrors().size(), 1);
        Assert.assertEquals(errors.getErrors().get(0), "Password is too short (minimum is 8 characters)");
    }

}
