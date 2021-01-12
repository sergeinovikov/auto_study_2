package redmine.model.project;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import redmine.db.requests.ProjectRequests;
import redmine.model.Generatable;
import redmine.utils.StringGenerators;

import java.util.Date;
import java.util.Random;

/**
 * Класс-модель проекта в системе
 * Описание методов создачния, чтения, редактирования и удаления
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Project implements Generatable<Project> {
    private Integer id;
    private String name = "SergAuto" + StringGenerators.randomEnglishString(8);
    private String description = "NovAuto" + StringGenerators.randomEnglishString(8);
    private String identifier = StringGenerators.randomEnglishLowerString(15);
    private String homepage = null;
    private Boolean isPublic = new Random().nextBoolean();
    private String parentId = null;
    private Date createdOn = new Date();
    private Date updatedOn = new Date();
    private Boolean inheritMembers = false;

    private Integer status = new Random().nextInt(5) + 1;
    private Integer lft = new Random().nextInt(2) + 1;
    private Integer rgt = new Random().nextInt(5) + 1;

    private Integer defaultVersionId = null;
    private Integer defaultAssignedToId = null;

    @Override
    public Project read() {
        Project project = ProjectRequests.getProject(this);
        if (project == null)
            return null;
        return this;
    }

    @Override
    public Project update() {
        return ProjectRequests.updateProject(this);
    }

    @Override
    public Project create() {
        return ProjectRequests.addProject(this);
    }

    @Override
    public void delete() {
        Project project = ProjectRequests.getProject(this);
        if (project.id == null) {
            new IllegalArgumentException("Проект с данным Id не найден");
        } else {
            ProjectRequests.deleteProject(this);
        }
    }
}
