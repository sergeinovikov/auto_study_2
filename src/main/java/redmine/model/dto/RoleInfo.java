package redmine.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Класс-модель для роли, использумый в классе передчи данных роли
 */

@Data
@Accessors(chain = true)
public class RoleInfo {
    private Integer id;
    private String name;
    private Boolean assignable;
    private String issues_visibility;
    private String time_entries_visibility;
    private String users_visibility;
    private List<String> permissions;
}
