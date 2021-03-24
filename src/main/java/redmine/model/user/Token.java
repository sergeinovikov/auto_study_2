package redmine.model.user;

import lombok.Data;
import lombok.experimental.Accessors;
import redmine.ui.pages.helpers.CucumberName;
import redmine.utils.StringGenerators;

import java.time.LocalDateTime;

/**
 * Класс-модель API-ключа пользователя
 */

@Data
@Accessors(chain = true)
@CucumberName("Токен")
public class Token {
    @CucumberName("Id")
    private Integer id;
    @CucumberName("Id пользователя")
    private Integer userId;
    @CucumberName("Тип API")
    private Action action = Action.API;
    @CucumberName("Значение")
    private String value = StringGenerators.generateHexString(40);
    @CucumberName("Дата создания")
    private LocalDateTime createdOn = LocalDateTime.now();
    @CucumberName("Дата обновления")
    private LocalDateTime updatedOn = LocalDateTime.now();
}
