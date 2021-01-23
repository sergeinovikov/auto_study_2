package rest_tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.HttpMethods;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.model.dto.RoleDto;
import redmine.model.role.Permissions;
import redmine.model.role.Role;
import redmine.model.user.User;

import java.util.stream.Collectors;

public class RoleTest {

    User user;
    Role role;

    @BeforeMethod
    public void preparedFixturesForRoleTest(){
        user = new User();
        user.setAdmin(true);
        user.setStatus(1);
        user.generate();
        role = new Role().generate();
    }

    @Test
    public void getRoleByIdTest() {
        ApiClient apiClient = new RestApiClient(user);
        String uri = String.format("roles/%d.json", role.getId());
        Request request = new RestRequest(uri, HttpMethods.GET, null, null, null);
        Response response = apiClient.executeRequest(request);

        Assert.assertEquals(response.getStatusCode(), 200);

        RoleDto roleDto = response.getBody(RoleDto.class);


        Assert.assertEquals(roleDto.getRole().getId(), role.getId());
        Assert.assertEquals(roleDto.getRole().getName(), role.getName());
        Assert.assertEquals(roleDto.getRole().getAssignable(), role.getAssignable());
        Assert.assertEquals(roleDto.getRole().getUsers_visibility(), role.getUsersVisibility().toString().toLowerCase());
        Assert.assertEquals(roleDto.getRole().getIssues_visibility(), role.getIssuesVisibility().toString().toLowerCase());
        Assert.assertEquals(roleDto.getRole().getPermissions().size(), role.getPermissions().size());
        Assert.assertEquals(roleDto.getRole().getPermissions(),
                role.getPermissions().stream().map(Permissions::toString).collect(Collectors.toList())
        );
    }
}
