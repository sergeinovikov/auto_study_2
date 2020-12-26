package redmine.model.project;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Project {
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
}
