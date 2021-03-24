package redmine.model.user;

import lombok.Data;
import lombok.experimental.Accessors;
import redmine.ui.pages.helpers.CucumberName;
import redmine.utils.StringGenerators;

import java.time.LocalDateTime;

/**
 * Класс-модель электронного почтового адреса пользователя
 */

@Data
@Accessors(chain = true)
@CucumberName("Почта")
public class EmailAddress {
    @CucumberName("Id")
    private Integer id;
    @CucumberName("Id пользователя")
    private Integer userId;
    @CucumberName("Адрес")
    private String address = StringGenerators.randomEmail(8);
    @CucumberName("По умолчанию")
    private Boolean isDefault = true;
    @CucumberName("Уведомления")
    private Boolean notify = true;
    @CucumberName("Дата создания")
    private LocalDateTime createdOn = LocalDateTime.now();
    @CucumberName("Дата обновления")
    private LocalDateTime updatedOn = LocalDateTime.now();
}
