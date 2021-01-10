package sql_tests;

import org.junit.Assert;
import org.junit.Test;
import redmine.db.requests.RoleRequests;
import redmine.model.role.*;
import redmine.model.user.User;
import java.util.HashSet;


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
    public void updateRoleTest() {
        Role role = new Role();
        role.setName("SergAutoPjRUGxDj");
        role.setAssignable(false);
        Role updatedRole = role.update();
        Assert.assertEquals(role.getAssignable(), updatedRole.getAssignable());
    }

    @Test
    public void generateRoleTest() {
        Role role = new Role();
        role.setPermissions(new RolePermissions((new HashSet<>())));

        role.generate();

        Role createdRole = new Role();
        createdRole.setPermissions(role.getPermissions());

        role.setPermissions(new RolePermissions(
                Permissions.ADD_DOCUMENTS,
                Permissions.ADD_ISSUES,
                Permissions.ADD_PROJECT
        ));

        role.generate();

        Role updatedRole = new Role();
        updatedRole.setPermissions(role.getPermissions());

        Assert.assertNotEquals(updatedRole.getPermissions(), createdRole.getPermissions());
    }

    @Test
    public void deleteRoleTest() {
        Role roleForDeleting = new Role().generate();
        Role checkExistingRole = roleForDeleting.read();

        Assert.assertEquals(roleForDeleting.getId(), checkExistingRole.getId());

        roleForDeleting.delete();
        roleForDeleting = checkExistingRole.read();

        Assert.assertNull(roleForDeleting);
    }

    @Test
    public void getUserTest() {
        User user = new User();
        user.setLogin("bywggyvg");

        User dataBaseUser = user.read();

        Assert.assertEquals(dataBaseUser.getLogin(), "bywggyvg");
    }

    @Test
    public void addUserTest() {
        User user = new User();
        user.setFirstName("Тестовый Сергей");
        User dataBaseNewUser = user.create();
        Assert.assertEquals(user.getLogin(), dataBaseNewUser.getLogin());
    }

    @Test
    public void updateUserTest() {
        User user = new User();
        user.setLogin("drtpzzqu");
        user.setFirstName("updateUser");
        User dataBaseUpdateUser = user.update();
        Assert.assertEquals(user.getLogin(), dataBaseUpdateUser.getLogin());
    }

    @Test
    public void deleteUserTest() {
        User userForDeleting = new User().generate();
        User checkExistingUser = userForDeleting.read();

        Assert.assertEquals(userForDeleting.getId(), checkExistingUser.getId());

        userForDeleting.delete();
        userForDeleting = checkExistingUser.read();

        Assert.assertNull(userForDeleting);
    }

    /*@Test //TODO
    public void generateUserTest() {
        User user = new User();
        user.setLogin("LoginForEditing");

        user.generate();

        User createdUser = new User();
        createdUser.setLogin(user.getLogin());

        user.setLogin("ChangedLogin");

        user.generate();

        User updatedUser = new User();
        updatedUser.setLogin(user.getLogin());

        Assert.assertNotEquals(updatedUser.getLogin(), createdUser.getLogin());
    }*/
}
