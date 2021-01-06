package redmine.model.role;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RolePermissions extends HashSet<Permissions> {

    public RolePermissions(Permissions... permissions) {
        this.addAll(Arrays.asList(permissions));
    }

    public RolePermissions(Set<Permissions> permissions) {
        this.addAll(permissions);
    }

    @Override
    public String toString() {
        StringBuilder rolePermissions = new StringBuilder("---\n");
        forEach(permission -> rolePermissions.append("- :").append(permission).append("\n"));
        return rolePermissions.toString();
    }


    public static RolePermissions of(String stringValue) {
        Set<Permissions> permissions = Stream.of(stringValue.split("\n"))
                .filter(str -> str.startsWith("- :"))
                .map(str -> str.substring(3).toUpperCase())
                .map(Permissions::valueOf)
                .collect(Collectors.toSet());
        return new RolePermissions(permissions);
    }
}
