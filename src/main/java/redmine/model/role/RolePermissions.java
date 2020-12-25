package redmine.model.role;

import java.util.HashSet;

public class RolePermissions extends HashSet<Permissions> {

    @Override
    public String toString() {
        StringBuilder rolePermissions = new StringBuilder("---\n");
        forEach(permission -> rolePermissions.append("- :").append(permission).append("\n"));
        return rolePermissions.toString();
    }

}
