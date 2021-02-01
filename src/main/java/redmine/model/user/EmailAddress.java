package redmine.model.user;

import lombok.*;
import lombok.experimental.Accessors;
import redmine.utils.StringGenerators;

import java.util.Date;

/**
 * Класс-модель электронного почтового адреса пользователя
 */

@Data
@Accessors(chain = true)
public class EmailAddress {
    private Integer id;
    private Integer userId;
    private String address = StringGenerators.randomEmail();
    private Boolean isDefault = true;
    private Boolean notify = true;
    private Date createdOn = new Date();
    private Date updatedOn = new Date();
}
