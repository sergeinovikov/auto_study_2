package redmine.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleInfo {
    private Integer id;
    private String name;
    private Boolean assignable;
    private String issues_visibility;
    private String time_entries_visibility;
    private String users_visibility;
    private List<String> permissions;
}
