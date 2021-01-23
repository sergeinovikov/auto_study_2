package redmine.model.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import redmine.utils.StringGenerators;

import java.util.Date;

/**
 * Класс-модель электронного почтового адреса пользователя
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EmailAddress {
    private Integer id;
    private Integer userId;
    private String address = StringGenerators.randomEmail();
    private Boolean isDefault = true;
    private Boolean notify = true;
    private Date createdOn = new Date();
    private Date updatedOn = new Date();
}
