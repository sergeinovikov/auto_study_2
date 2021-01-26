package sql_tests;

import org.testng.annotations.AfterClass;
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
        role.setName("Пользователь1");

        Role dataBaseRole = RoleRequests.getRole(role);

        Assert.assertEquals(dataBaseRole.getId().intValue(), 11);

        role.setId(33);
        Role dataBaseRole2 = RoleRequests.getRole(role);
        Assert.assertEquals(dataBaseRole2.getName(), "Генерируемая роль5");
    }

    @Test
    public void updateRoleTest() {
        Role role = new Role();
        role.setName("Первая роль Сергея");
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
    }

    @AfterClass
    public static void afterClass() {
        Role roleChangeAssignable = new Role();
        roleChangeAssignable.setName("Первая роль Сергея");
        roleChangeAssignable.setAssignable(true);
        roleChangeAssignable.update();
    }
}
