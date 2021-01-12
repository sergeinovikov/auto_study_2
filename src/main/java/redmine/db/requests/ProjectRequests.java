package redmine.db.requests;

import redmine.managers.Manager;
import redmine.model.project.Project;
import redmine.utils.DateFormatter;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс описывающий создание, чтение, редактирование и обновление проектов в БД
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
                            project.setCreatedOn((Date) map.get("created_on"));
                            project.setUpdatedOn((Date) map.get("updated_on"));
                            project.setIdentifier((String) map.get("identifier"));
                            project.setStatus((Integer) map.get("status"));
                            project.setLft((Integer) map.get("lft"));
                            project.setRgt((Integer) map.get("rgt"));
                            project.setInheritMembers((Boolean) map.get("inherit_embers"));
                            project.setDefaultVersionId((Integer) map.get("default_version_id"));
                            project.setDefaultAssignedToId((Integer) map.get("default_assigned_to_id"));
                            return project;
                        }
                )
                .collect(Collectors.toList());
    }

    public static Project getProject(Project objectProject) {
        return getAllProjects().stream()
                .filter(project -> {
                            if (objectProject.getId() == null) {
                                return objectProject.getName().equals(project.getName());
                            } else {
                                return objectProject.getId().equals(project.getId());
                            }
                        }
                )
                .findFirst()
                .orElse(null);
    }

    public static Project updateProject(Project project) {
        String query = "UPDATE public.projects\n" +
                "SET description=?, homepage=?, is_public=?, parent_id=?, created_on=?, updated_on=?, identifier=?, status=?, lft=?, rgt=?, inherit_members=?, default_version_id=?, default_assigned_to_id=?\n" +
                "WHERE name=? RETURNING id;\n";
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query,
                project.getDescription(),
                project.getHomepage(),
                project.getIsPublic(),
                project.getParentId(),
                DateFormatter.convertDate(project.getCreatedOn()),
                DateFormatter.convertDate(project.getUpdatedOn()),
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
                DateFormatter.convertDate(project.getCreatedOn()),
                DateFormatter.convertDate(project.getUpdatedOn()),
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
