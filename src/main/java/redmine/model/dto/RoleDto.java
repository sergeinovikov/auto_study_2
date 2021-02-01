package redmine.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Класс передачи данных роли между REST-ответом и Java-приложением
 */

@Data
@Accessors(chain = true)
public class RoleDto {

    private RoleInfo role;
}
