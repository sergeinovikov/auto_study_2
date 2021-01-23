package redmine.api.interfaces;

import java.util.Map;

/**
 * Класс-интерфейс, обозначающий методы REST-ответа
 */

public interface Response {

    int getStatusCode();

    Map<String, String> getHeaders();

    Object getBody();

    <T> T getBody(Class<T> clazz);

}
