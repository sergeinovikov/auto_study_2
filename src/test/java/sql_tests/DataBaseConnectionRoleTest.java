package sql_tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import redmine.db.requests.RoleRequests;
import redmine.model.role.*;

import java.util.HashSet;


public class DataBaseConnectionRoleTest {

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
        role.setName("Пользователь11234");
        role.create();

        Role dataBaseRole = role.read();

        Assert.assertEquals(dataBaseRole.getName(), role.getName());

        Role role1 = new Role().setId(1);
        Role dataBaseRole2 = role1.read();
        Assert.assertEquals(dataBaseRole2.getName(), "Non member");
    }

    @Test
    public void updateRoleTest() {
        Role role = new Role();
        role.setName("Перваяd роль Сергея");
        role.generate();
        role.setAssignable(false);
        Role updatedRole = role.update();
        Assert.assertEquals(role.getAssignable(), updatedRole.getAssignable());
    }

    @Test
    public void generateRoleTest() {
        Role originalRole = new Role();
        originalRole.setPermissions(new RolePermissions((new HashSet<>())));

        originalRole.generate(); //создаём сущность в БД

        Role createdRoleWithoutPermissions = new Role();
        createdRoleWithoutPermissions.setPermissions(originalRole.getPermissions());

        originalRole.setPermissions(new RolePermissions(
                Permissions.ADD_DOCUMENTS,
                Permissions.ADD_ISSUES,
                Permissions.ADD_PROJECT
        ));

        originalRole.generate(); //обновляем сущегость в БД

        Assert.assertNotEquals(originalRole.getPermissions(), createdRoleWithoutPermissions.getPermissions());
    }

    @Test
    public void deleteRoleTest() {
        Role roleForDeleting = new Role().generate();
        Role checkExistingRole = roleForDeleting.read();

        Assert.assertEquals(roleForDeleting.getId(), checkExistingRole.getId());

        roleForDeleting.delete();
        roleForDeleting = checkExistingRole.read();

        Assert.assertNull(roleForDeleting);

        Role role1 = new Role();
        role1.delete();
    }

}
