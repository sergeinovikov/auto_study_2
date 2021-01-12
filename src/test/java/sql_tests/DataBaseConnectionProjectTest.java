package sql_tests;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import redmine.model.project.Project;

public class DataBaseConnectionProjectTest {

    @Test
    public void getProjectTest() {
        Project project = new Project();
        project.setName("ТестовыйПроектСергея");

        Project dataBaseProject = project.read();

        Assert.assertEquals(dataBaseProject.getName(), "ТестовыйПроектСергея");
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
        project.setDescription("Тестовое описание Сергея");
        Project dataBaseProject = project.update();
        Assert.assertEquals(project.getDescription(), dataBaseProject.getDescription());
    }

    @Test
    public void deleteProjectTest() {
        Project projectForDeleting = new Project().generate();
        Project checkExistingProject = projectForDeleting.read();

        Assert.assertEquals(projectForDeleting.getId(), checkExistingProject.getId());

        projectForDeleting.delete();
        projectForDeleting = checkExistingProject.read();

        Assert.assertNull(projectForDeleting);
    }

    @Test
    public void generateProjectTest() {
        Project originalProject = new Project();
        originalProject.setDescription("DescForEditing");

        originalProject.generate(); //создаём проект

        Project projectWithSameDesc = new Project();
        projectWithSameDesc.setDescription(originalProject.getDescription());

        originalProject.setDescription("ChangedDesc");

        originalProject.generate(); //обновляем проект

        Assert.assertNotEquals(originalProject.getDescription(), projectWithSameDesc.getDescription());
    }

    @AfterClass
    public static void afterClass() {
        Project projectChangeDesc = new Project();
        projectChangeDesc.setName("projectForUpdate");
        projectChangeDesc.setDescription("Description for changing");
        projectChangeDesc.update();
    }
}
