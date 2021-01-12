package sql_tests;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import redmine.model.user.User;

public class DataBaseConnectionUserTest {

    @Test
    public void getUserTest() {
        User user = new User();
        user.setLogin("admin");

        User dataBaseUser = user.read();

        Assert.assertEquals(dataBaseUser.getLogin(), "admin");
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
        user.setLogin("ljujqkip");
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

    @Test
    public void generateUserTest() {
        User originalUser = new User();
        originalUser.setFirstName("NameForEditing");

        originalUser.generate(); //создаём пользователя

        User userWithSameName = new User();
        userWithSameName.setFirstName(originalUser.getFirstName());

        originalUser.setFirstName("ChangedName");

        originalUser.generate(); //обновляем пользователя

        Assert.assertNotEquals(originalUser.getFirstName(), userWithSameName.getFirstName());
    }

    @AfterClass
    public static void afterClass() {
        User userChangeLogin = new User();
        userChangeLogin.setLogin("ljujqkip");
        userChangeLogin.setFirstName("needToUpdate");
        userChangeLogin.update();
    }
}
