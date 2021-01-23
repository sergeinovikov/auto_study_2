package redmine.model.dto;

import lombok.Data;

import java.util.List;

/**
 * Класс передачи данных ошибок между REST-ответом и Java-приложением
 */

@Data
public class UserCreationError {

    private List<String> errors;
}
