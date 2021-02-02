package redmine.model.user;

import lombok.Data;
import lombok.experimental.Accessors;
import redmine.utils.StringGenerators;

import java.time.LocalDateTime;

/**
 * Класс-модель электронного почтового адреса пользователя
 */

@Data
@Accessors(chain = true)
public class EmailAddress {
    private Integer id;
    private Integer userId;
    private String address = StringGenerators.randomEmail(8);
    private Boolean isDefault = true;
    private Boolean notify = true;
    private LocalDateTime createdOn = LocalDateTime.now();
    private LocalDateTime updatedOn = LocalDateTime.now();
}
