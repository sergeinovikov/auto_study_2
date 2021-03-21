package redmine.model.role;

import lombok.*;
import lombok.experimental.Accessors;
import redmine.db.requests.RoleRequests;
import redmine.model.Generatable;
import redmine.utils.StringGenerators;

import java.util.Random;

/**
 * Класс-модель роли в системе
 * Описание методов создачния, чтения, редактирования и удаления
 */

@Data
@Accessors(chain = true)
public class Role implements Generatable<Role> {
    private Integer id;
    private String name = "SergAuto" + StringGenerators.randomEnglishString(8);
    private Integer position = 1;
    private Integer builtin = 0;
    private Boolean assignable = true;
    private IssuesVisibility issuesVisibility = IssuesVisibility.values()[new Random().nextInt(IssuesVisibility.values().length)];
    private UsersVisibility usersVisibility = UsersVisibility.values()[new Random().nextInt(UsersVisibility.values().length)];
    private RolePermissions permissions = new RolePermissions().generatePermissions();
    private TimeEntriesVisibility timeEntriesVisibility = TimeEntriesVisibility.ALL;
    private Boolean allRolesManaged = true;
    private String settings = "--- !ruby/hash:ActiveSupport::HashWithIndifferentAccess\n" +
            "permissions_all_trackers: !ruby/hash:ActiveSupport::HashWithIndifferentAccess\n" +
            "  view_issues: '1'\n" +
            "  add_issues: '1'\n" +
            "  edit_issues: '1'\n" +
            "  add_issue_notes: '1'\n" +
            "  delete_issues: '1'\n" +
            "permissions_tracker_ids: !ruby/hash:ActiveSupport::HashWithIndifferentAccess\n" +
            "  view_issues: []\n" +
            "  add_issues: []\n" +
            "  edit_issues: []\n" +
            "  add_issue_notes: []\n" +
            "  delete_issues: []\n";

    @Override
    public Role read() {
        return this.id == null
                ? RoleRequests.getRoleByName(this.name)
                : RoleRequests.getRoleById(this.id);
    }

    @Override
    public Role update() {
        return this.id == null
                ? RoleRequests.updateByName(this)
                : RoleRequests.updateById(this);
    }

    @Override
    public Role create() {
        return RoleRequests.addRole(this);
    }

    @Override
    public void delete() {
        if (this.read() != null) {
            RoleRequests.deleteRole(this);
        } else {
            throw new IllegalArgumentException("Роль с данным Id не найдена");
        }
    }

    @Override
    public String toString() {
        return "Role{" + System.lineSeparator() +
                "id=" + id + System.lineSeparator() +
                "name='" + name + '\'' + System.lineSeparator() +
                "position=" + position + System.lineSeparator() +
                "builtin=" + builtin + System.lineSeparator() +
                "assignable=" + assignable + System.lineSeparator() +
                "issuesVisibility=" + issuesVisibility + System.lineSeparator() +
                "usersVisibility=" + usersVisibility + System.lineSeparator() +
                "permissions=" + permissions + System.lineSeparator() +
                "timeEntriesVisibility=" + timeEntriesVisibility + System.lineSeparator() +
                "allRolesManaged=" + allRolesManaged + System.lineSeparator() +
                "settings='" + settings + '\'' + System.lineSeparator() +
                '}';
    }
}
