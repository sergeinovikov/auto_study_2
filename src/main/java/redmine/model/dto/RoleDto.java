package redmine.model.dto;

import lombok.Data;

/**
 * Класс передачи данных роли между REST-ответом и Java-приложением
 */

@Data
public class RoleDto {

    private RoleInfo role;
}
