package redmine.model.role;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import redmine.model.Generatable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Role implements Generatable<Role> {
    private Integer id;
    private Integer position;
    private Integer builtin;
    private String name;
    private Boolean assignable;
    private IssuesVisibility issuesVisibility;
    private UsersVisibility usersVisibility;
    private RolePermissions permissions;
    private TimeEntriesVisibility timeEntriesVisibility;
    private Boolean allRolesManaged;
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
        //TODO получение из БД
        return null;
    }

    @Override
    public Role update() {
        //TODO обновление в БД
        return null;
    }

    @Override
    public Role create() {
        //TODO создание в БД
        return null;
    }

    @Override
    public Role generate() {
        return null;
    }
}
