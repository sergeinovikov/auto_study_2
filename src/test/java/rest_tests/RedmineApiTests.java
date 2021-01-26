package rest_tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.HttpMethods;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.model.user.User;

public class RedmineApiTests {
    User user;

    @BeforeClass
    public void preparedFixtures() {
        user = new User().setAdmin(true).setStatus(1).generate();
    }

    @Test
    public void testUserGet() {
        ApiClient apiClient = new RestApiClient(user);
        Request request = new RestRequest("users.json", HttpMethods.GET, null, null, null);
        Response response = apiClient.executeRequest(request);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getHeaders().containsKey("Content-Type"));
    }
}
