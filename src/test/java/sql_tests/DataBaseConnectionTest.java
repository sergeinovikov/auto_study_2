package sql_tests;

import org.junit.Assert;
import org.junit.Test;
import redmine.db.requests.RoleRequests;
import redmine.managers.Manager;
import redmine.model.role.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class DataBaseConnectionTest {

    @Test
    public void basicSqlTest() {
        int rolesCountBefore = RoleRequests.getAllRoles().size();
        System.out.println(rolesCountBefore);

        Role role = new Role();
        RoleRequests.addRole(role);

        int rolesCountAfter = RoleRequests.getAllRoles().size();

        Assert.assertNotNull(role.getId());
        Assert.assertEquals(rolesCountAfter, rolesCountBefore + 1);

        System.out.println(RoleRequests.getAllRoles().size());
    }

    @Test
    public void getRoleTest() {
        Role role = new Role();
        role.setName("AutojDsNIEts");

        Role dataBaseRole = RoleRequests.getRole(role);

        Assert.assertEquals(dataBaseRole.getId().intValue(), 178);

        role.setId(178);
        Role dataBaseRole2 = RoleRequests.getRole(role);
        Assert.assertEquals(dataBaseRole2.getName(), "AutojDsNIEts");
    }

    @Test
    public void updateRole() {
        Role role = new Role();
        role.setName("SergAutosUKAdCrC");
        role.setAssignable(true);
        RoleRequests.updateRole(role);
    }

    @Test
    public void generateRole() {
        Role role = new Role();
        role.setName("Генер роль1");

        role.generate();

        role.setPermissions(new RolePermissions(
                Permissions.ADD_DOCUMENTS,
                Permissions.ADD_ISSUES,
                Permissions.ADD_PROJECT
        ));

        role.generate();
    }

    @Test
    public void deleteRole() {
        Role role = new Role();
        role.setId(170);

        role.delete();
    }
}
