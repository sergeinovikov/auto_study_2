package redmine.model.project;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import redmine.model.Generatable;
import redmine.model.user.User;

import java.util.Date;

/**
 * Класс-модель проекта в системе
 * Описание методов создачния, чтения, редактирования и удаления
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Project implements Generatable<User> {
    private Integer id;
    private String name;
    private String description;
    private String identifier;
    private String homepage;
    private Boolean isPublic;
    private String parentId;
    private Date createdOn;
    private Date updatedOn;
    private Boolean inheritMembers;

    private Integer status;
    private Integer lft;
    private Integer rgt;

    private Integer defaultVersionId;
    private Integer defaultAssignedToId;

    @Override
    public User read() {
        //TODO
        return null;
    }

    @Override
    public User update() {
        //TODO
        return null;
    }

    @Override
    public User create() {
        //TODO
        return null;
    }

    @Override
    public void delete() {
        //TODO
    }
}
