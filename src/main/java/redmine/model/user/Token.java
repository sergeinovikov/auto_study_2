package redmine.model.user;

import lombok.Data;
import lombok.experimental.Accessors;
import redmine.utils.StringGenerators;

import java.util.Date;

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
    private Date createdOn = new Date();
    private Date updatedOn = new Date();
}
