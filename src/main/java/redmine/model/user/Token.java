package redmine.model.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import redmine.utils.CryptoGenerator;

import java.util.Date;

/**
 * Класс-модель API-ключа пользователя
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Token {
    private Integer id;
    private Integer userId;
    private String action = "api";
    private String value = CryptoGenerator.generateHEX(40);
    private Date createdOn = new Date();
    private Date updatedOn = new Date();
}
