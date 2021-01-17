package rest_tests;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.junit.Test;
import org.testng.Assert;
import redmine.model.dto.UserCreationError;
import redmine.model.dto.UserDto;
import redmine.model.user.Language;
import redmine.model.user.User;
import redmine.utils.gson.GsonHelper;

import static io.restassured.RestAssured.given;

public class MyFirstRestTests {

    @Test
    public void restRequestTest() {
        given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .request(Method.GET, "roles.JSON")
                .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON);
    }

    @Test
    public void createUserTest() {
        User admin = new User();
        admin.setAdmin(true);
        admin.setStatus(1);
        admin.generate();

        User user = new User();
        user.setStatus(1);
        user.setAdmin(false);

        String body = String.format("{\n" +
                "    \"user\": {\n" +
                "        \"login\": \"%s\",\n" +
                "        \"firstname\": \"%s\",\n" +
                "        \"lastname\": \"%s\",\n" +
                "        \"mail\": \"%s\",\n" +
                "        \"password\": \"1qazd@sS\" \n" +
                "    }\n" +
                "}", user.getLogin(), user.getFirstName(), user.getLastName(), user.getEmail().getAddress());
        Response response = given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .header("X-Redmine-API-Key", admin.getApiToken().getValue())
                .body(body)
                .request(Method.POST, "users.json");

        Assert.assertEquals(response.getStatusCode(), 201);
        String responseBody = response.getBody().asString();

        UserDto createdUser = GsonHelper.getGson().fromJson(responseBody, UserDto.class);

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
        admin.setLanguage(Language.RU);
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
        Response response = given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .header("X-Redmine-API-Key", admin.getApiToken().getValue())
                .body(body)
                .request(Method.POST, "users.json");

        Assert.assertEquals(response.getStatusCode(), 422);

        UserCreationError errors = GsonHelper.getGson().fromJson(response.getBody().asString(), UserCreationError.class);

        Assert.assertEquals(errors.getErrors().size(), 1);
        Assert.assertEquals(errors.getErrors().get(0), "Пароль недостаточной длины (не может быть меньше 8 символа)");
    }

}
