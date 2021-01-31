package sql_tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import redmine.model.project.Project;
import redmine.model.user.User;

public class DataBaseConnectionProjectTest {

    @Test
    public void getProjectTest() {
        Project project = new Project();
        project.setName("ТестовыйПроектСергея");
        project.create();

        Project dataBaseProject = project.read();

        Assert.assertEquals(dataBaseProject.getName(), project.getName());

        Project project1 = new Project().setId(1);
        Project dataBaseProject2 = project1.read();
        Assert.assertEquals(dataBaseProject2.getName(), "Ручной проект");
    }

    @Test
    public void addProjectTest() {
        Project project = new Project();
        project.setName("projectForUpdate");
        Project dataBaseProject = project.create();
        Assert.assertEquals(project.getName(), dataBaseProject.getName());
    }

    @Test
    public void updateProjectTest() {
        Project project = new Project();
        project.setName("projectForUpdate");

        Project existedProject = project.read();

        project.setDescription("Тестовое описание Сергея12");
        Project dataBaseProject = project.update();
        Assert.assertEquals(project.getDescription(), dataBaseProject.getDescription());

        User user = new User();
        user.setLogin("dbTest");
        User existedUser = user.read();
        existedUser.setFirstName("success2");
        User dataBaseUpdateUser = existedUser.update();
        Assert.assertEquals(user.getLogin(), dataBaseUpdateUser.getLogin());
    }

    @Test
    public void deleteProjectTest() {
        Project projectForDeleting = new Project().generate();
        Project checkExistingProject = projectForDeleting.read();

        Assert.assertEquals(projectForDeleting.getId(), checkExistingProject.getId());

        projectForDeleting.delete();
        projectForDeleting = checkExistingProject.read();

        Assert.assertNull(projectForDeleting);

        Project project12 = new Project();
        project12.delete();
    }

    @Test
    public void generateProjectTest() {
        Project originalProject = new Project();
        originalProject.setDescription("DescForEditing");

        originalProject.generate(); //создаём проект

        System.out.println(originalProject.getId());

        Project projectWithSameDesc = new Project();
        projectWithSameDesc.setDescription(originalProject.getDescription());

        originalProject.setDescription("ChangedDesc");

        originalProject.generate(); //обновляем проект

        Assert.assertNotEquals(originalProject.getDescription(), projectWithSameDesc.getDescription());


    }

}
