package redmine.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Класс-модель для роли, использумый в классе передчи данных роли
 */

public class RoleInfo {
    private Integer id;
    private String name;
    private Boolean assignable;
    private String issues_visibility;
    private String time_entries_visibility;
    private String users_visibility;
    private List<String> permissions;


    public RoleInfo(Integer id, String name, Boolean assignable, String issues_visibility, String time_entries_visibility, String users_visibility, List<String> permissions) {
        this.id = id;
        this.name = name;
        this.assignable = assignable;
        this.issues_visibility = issues_visibility;
        this.time_entries_visibility = time_entries_visibility;
        this.users_visibility = users_visibility;
        this.permissions = permissions;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getAssignable() {
        return assignable;
    }

    public String getIssues_visibility() {
        return issues_visibility;
    }

    public String getTime_entries_visibility() {
        return time_entries_visibility;
    }

    public String getUsers_visibility() {
        return users_visibility;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public RoleInfo setId(Integer id) {
        this.id = id;
        return this;
    }

    public RoleInfo setName(String name) {
        this.name = name;
        return this;
    }

    public RoleInfo setAssignable(Boolean assignable) {
        this.assignable = assignable;
        return this;
    }

    public RoleInfo setIssues_visibility(String issues_visibility) {
        this.issues_visibility = issues_visibility;
        return this;
    }

    public RoleInfo setTime_entries_visibility(String time_entries_visibility) {
        this.time_entries_visibility = time_entries_visibility;
        return this;
    }

    public RoleInfo setUsers_visibility(String users_visibility) {
        this.users_visibility = users_visibility;
        return this;
    }

    public RoleInfo setPermissions(List<String> permissions) {
        this.permissions = permissions;
        return this;
    }

    public RoleInfo() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleInfo)) return false;

        RoleInfo roleInfo = (RoleInfo) o;

        if (id != null ? !id.equals(roleInfo.id) : roleInfo.id != null) return false;
        if (name != null ? !name.equals(roleInfo.name) : roleInfo.name != null) return false;
        if (assignable != null ? !assignable.equals(roleInfo.assignable) : roleInfo.assignable != null) return false;
        if (issues_visibility != null ? !issues_visibility.equals(roleInfo.issues_visibility) : roleInfo.issues_visibility != null)
            return false;
        if (time_entries_visibility != null ? !time_entries_visibility.equals(roleInfo.time_entries_visibility) : roleInfo.time_entries_visibility != null)
            return false;
        if (users_visibility != null ? !users_visibility.equals(roleInfo.users_visibility) : roleInfo.users_visibility != null)
            return false;
        return permissions != null ? permissions.equals(roleInfo.permissions) : roleInfo.permissions == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (assignable != null ? assignable.hashCode() : 0);
        result = 31 * result + (issues_visibility != null ? issues_visibility.hashCode() : 0);
        result = 31 * result + (time_entries_visibility != null ? time_entries_visibility.hashCode() : 0);
        result = 31 * result + (users_visibility != null ? users_visibility.hashCode() : 0);
        result = 31 * result + (permissions != null ? permissions.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RoleInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", assignable=" + assignable +
                ", issues_visibility='" + issues_visibility + '\'' +
                ", time_entries_visibility='" + time_entries_visibility + '\'' +
                ", users_visibility='" + users_visibility + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
