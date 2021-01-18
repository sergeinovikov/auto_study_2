package redmine.model.role;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import redmine.db.requests.RoleRequests;
import redmine.model.Generatable;
import redmine.model.user.Language;
import redmine.utils.StringGenerators;

import java.util.HashSet;
import java.util.Random;

/**
 * Класс-модель роли в системе
 * Описание методов создачния, чтения, редактирования и удаления
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
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
        Role role = RoleRequests.getRole(this);
        if (role == null)
            return null;
        return this;
    }

    @Override
    public Role update() {
        return RoleRequests.updateRole(this);
    }

    @Override
    public Role create() {
        return RoleRequests.addRole(this);
    }

    @Override
    public void delete() {
        Role role = RoleRequests.getRole(this);
        if (role == null) {
           new IllegalArgumentException("Роль с данным Id не найдена");
        } else {
            RoleRequests.deleteRole(this);
        }
    }
}
