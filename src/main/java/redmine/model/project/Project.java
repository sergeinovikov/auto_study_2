package redmine.model.project;

import lombok.*;
import lombok.experimental.Accessors;
import redmine.db.requests.ProjectRequests;
import redmine.model.Generatable;
import redmine.utils.StringGenerators;

import java.util.Date;
import java.util.Random;

/**
 * Класс-модель проекта в системе
 * Описание методов создачния, чтения, редактирования и удаления
 */

@Data
@Accessors(chain = true)
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
        Project project = this.read();
        if (project == null || project.id == null) {
            new IllegalArgumentException("Проект с данным Id не найден");
        } else {
            ProjectRequests.deleteProject(this);
        }
    }
}
