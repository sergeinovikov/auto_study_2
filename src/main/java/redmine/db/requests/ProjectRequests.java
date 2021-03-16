package redmine.db.requests;

import redmine.managers.Manager;
import redmine.model.project.Project;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс описывающий создание, чтение, редактирование и удаление проектов в БД
 */

public class ProjectRequests {

    public static List<Project> getAllProjects() {
        String query = "SELECT * FROM projects";
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        return result.stream()
                .map(map -> {
                            Project project = new Project();
                            project.setId((Integer) map.get("id"));
                            project.setName((String) map.get("name"));
                            project.setDescription((String) map.get("description"));
                            project.setHomepage((String) map.get("homepage"));
                            project.setIsPublic((Boolean) map.get("is_public"));
                            project.setCreatedOn(
                                    map.get("created_on") == null
                                            ? null
                                            : ((Timestamp) map.get("created_on")).toLocalDateTime()
                            );
                            project.setUpdatedOn(
                                    map.get("updated_on") == null
                                            ? null
                                            : ((Timestamp) map.get("updated_on")).toLocalDateTime()
                            );
                            project.setIdentifier((String) map.get("identifier"));
                            project.setStatus((Integer) map.get("status"));
                            project.setLft((Integer) map.get("lft"));
                            project.setRgt((Integer) map.get("rgt"));
                            project.setInheritMembers((Boolean) map.get("inherit_members"));
                            project.setDefaultVersionId((Integer) map.get("default_version_id"));
                            project.setDefaultAssignedToId((Integer) map.get("default_assigned_to_id"));
                            return project;
                        }
                )
                .collect(Collectors.toList());
    }

    public static Project getProjectByName(String name) {
        String query = "SELECT * FROM projects WHERE name=?";
        return getProject(query, name);
    }

    public static Project getProjectById(Integer id) {
        String query = "SELECT * FROM projects WHERE id=?";
        return getProject(query, id);
    }

    private static Project getProject(String query, Object... parameters) {
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query, parameters);
        Project projectFromDb = result.stream()
                .map(map -> {
                            Project project = new Project();
                            project.setId((Integer) map.get("id"));
                            project.setName((String) map.get("name"));
                            project.setDescription((String) map.get("description"));
                            project.setHomepage((String) map.get("homepage"));
                            project.setIsPublic((Boolean) map.get("is_public"));
                            project.setCreatedOn(((Timestamp) map.get("created_on")).toLocalDateTime());
                            project.setUpdatedOn(((Timestamp) map.get("updated_on")).toLocalDateTime());
                            project.setIdentifier((String) map.get("identifier"));
                            project.setStatus((Integer) map.get("status"));
                            project.setLft((Integer) map.get("lft"));
                            project.setRgt((Integer) map.get("rgt"));
                            project.setInheritMembers((Boolean) map.get("inherit_members"));
                            project.setDefaultVersionId((Integer) map.get("default_version_id"));
                            project.setDefaultAssignedToId((Integer) map.get("default_assigned_to_id"));
                            return project;
                        }
                )
                .findFirst()
                .orElse(null);

        return projectFromDb;
    }

    public static Project updateByName(Project project) {
        String query = "UPDATE public.projects\n" +
                "SET description=?, homepage=?, is_public=?, parent_id=?, created_on=?, updated_on=?, identifier=?, status=?, lft=?, rgt=?, inherit_members=?, default_version_id=?, default_assigned_to_id=?\n" +
                "WHERE \"name\"=? RETURNING id;\n";
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query,
                project.getDescription(),
                project.getHomepage(),
                project.getIsPublic(),
                project.getParentId(),
                project.getCreatedOn(),
                project.getUpdatedOn(),
                project.getIdentifier(),
                project.getStatus(),
                project.getLft(),
                project.getRgt(),
                project.getInheritMembers(),
                project.getDefaultVersionId(),
                project.getDefaultAssignedToId(),
                project.getName()
        );
        project.setId((Integer) result.get(0).get("id"));

        return project;
    }

    public static Project updateById(Project project) {
        String query = "UPDATE public.projects\n" +
                "SET \"name\"=?, description=?, homepage=?, is_public=?, parent_id=?, created_on=?, updated_on=?, identifier=?, status=?, lft=?, rgt=?, inherit_members=?, default_version_id=?, default_assigned_to_id=?\n" +
                "WHERE id=? RETURNING id;\n";
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query,
                project.getName(),
                project.getDescription(),
                project.getHomepage(),
                project.getIsPublic(),
                project.getParentId(),
                project.getCreatedOn(),
                project.getUpdatedOn(),
                project.getIdentifier(),
                project.getStatus(),
                project.getLft(),
                project.getRgt(),
                project.getInheritMembers(),
                project.getDefaultVersionId(),
                project.getDefaultAssignedToId(),
                project.getId()
        );
        project.setId((Integer) result.get(0).get("id"));

        return project;
    }


    public static Project addProject(Project project) {
        String query = "INSERT INTO public.projects\n" +
                "(id, \"name\", description, homepage, is_public, parent_id, created_on, updated_on, identifier, status, lft, rgt, inherit_members, default_version_id, default_assigned_to_id)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;\n";
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query,
                project.getName(),
                project.getDescription(),
                project.getHomepage(),
                project.getIsPublic(),
                project.getParentId(),
                project.getCreatedOn(),
                project.getUpdatedOn(),
                project.getIdentifier(),
                project.getStatus(),
                project.getLft(),
                project.getRgt(),
                project.getInheritMembers(),
                project.getDefaultVersionId(),
                project.getDefaultAssignedToId()
        );
        project.setId((Integer) result.get(0).get("id"));
        return project;
    }

    public static void deleteProject(Project project) {
        String query = "DELETE FROM public.projects\n" +
                "WHERE id=?;\n";
        Manager.dbConnection.executeDeleteQuery(query,
                project.getId()
        );
    }
}
