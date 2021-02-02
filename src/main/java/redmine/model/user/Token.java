package redmine.model.user;

import lombok.Data;
import lombok.experimental.Accessors;
import redmine.utils.StringGenerators;

import java.time.LocalDateTime;

/**
 * Класс-модель API-ключа пользователя
 */

@Data
@Accessors(chain = true)
public class Token {
    private Integer id;
    private Integer userId;
    private Action action = Action.API;
    private String value = StringGenerators.generateHexString(40);
    private LocalDateTime createdOn = LocalDateTime.now();
    private LocalDateTime updatedOn = LocalDateTime.now();
}
