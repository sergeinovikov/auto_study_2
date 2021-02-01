package redmine.model.user;

import lombok.*;
import lombok.experimental.Accessors;
import redmine.utils.CryptoGenerator;

import java.util.Date;

/**
 * Класс-модель API-ключа пользователя
 */

@Data
@Accessors(chain = true)
public class Token {
    private Integer id;
    private Integer userId;
    private String action = "api";
    private String value = CryptoGenerator.generateHEX(40);
    private Date createdOn = new Date();
    private Date updatedOn = new Date();
}
