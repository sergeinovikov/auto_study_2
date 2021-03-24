package redmine.model.project;

import lombok.Data;
import lombok.experimental.Accessors;
import redmine.db.requests.ProjectRequests;
import redmine.model.Generatable;
import redmine.ui.pages.helpers.CucumberName;
import redmine.utils.StringGenerators;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Класс-модель проекта в системе
 * Описание методов создачния, чтения, редактирования и удаления
 */

@Data
@Accessors(chain = true)
@CucumberName("Проект")
public class Project implements Generatable<Project> {
    @CucumberName("Id")
    private Integer id;
    @CucumberName("Имя")
    private String name = "SergAuto" + StringGenerators.randomEnglishString(8);
    @CucumberName("Описание")
    private String description = "NovAuto" + StringGenerators.randomEnglishString(8);
    @CucumberName("Идентификатор")
    private String identifier = StringGenerators.randomEnglishLowerString(15);
    @CucumberName("Домашняя страница")
    private String homepage = null;
    @CucumberName("Публичность")
    private Boolean isPublic = new Random().nextBoolean();
    @CucumberName("Id родительского проекта")
    private String parentId = null;
    @CucumberName("Время создания")
    private LocalDateTime createdOn = LocalDateTime.now();
    @CucumberName("Время обновления")
    private LocalDateTime updatedOn = LocalDateTime.now();
    @CucumberName("Наследники")
    private Boolean inheritMembers = false;

    @CucumberName("Статус")
    private Integer status = new Random().nextInt(5) + 1;
    @CucumberName("lft")
    private Integer lft = new Random().nextInt(2) + 1;
    @CucumberName("rgt")
    private Integer rgt = new Random().nextInt(5) + 1;

    @CucumberName("Версия id по умолчанию")
    private Integer defaultVersionId = null;
    @CucumberName("По умолчанию назначен Id")
    private Integer defaultAssignedToId = null;

    @Override
    public Project read() {
        return this.id == null
                ? ProjectRequests.getProjectByName(this.name)
                : ProjectRequests.getProjectById(this.id);
    }

    @Override
    public Project update() {
        return this.id == null
                ? ProjectRequests.updateByName(this)
                : ProjectRequests.updateById(this);
    }

    @Override
    public Project create() {
        return ProjectRequests.addProject(this);
    }

    @Override
    public void delete() {
        if (this.read() != null) {
            ProjectRequests.deleteProject(this);
        } else {
            throw new IllegalArgumentException("Проект с данным Id не найден");
        }
    }

    @Override
    public String toString() {
        return "Project{" + System.lineSeparator() +
                "id=" + id + System.lineSeparator() +
                "name='" + name + '\'' + System.lineSeparator() +
                "description='" + description + '\'' + System.lineSeparator() +
                "identifier='" + identifier + '\'' + System.lineSeparator() +
                "homepage='" + homepage + '\'' + System.lineSeparator() +
                "isPublic=" + isPublic + System.lineSeparator() +
                "parentId='" + parentId + '\'' + System.lineSeparator() +
                "createdOn=" + createdOn + System.lineSeparator() +
                "updatedOn=" + updatedOn + System.lineSeparator() +
                "inheritMembers=" + inheritMembers + System.lineSeparator() +
                "status=" + status + System.lineSeparator() +
                "lft=" + lft + System.lineSeparator() +
                "rgt=" + rgt + System.lineSeparator() +
                "defaultVersionId=" + defaultVersionId + System.lineSeparator() +
                "defaultAssignedToId=" + defaultAssignedToId + System.lineSeparator() +
                '}';
    }
}
