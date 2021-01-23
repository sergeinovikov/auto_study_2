package redmine.api.interfaces;

import java.util.Map;

/**
 * Класс-интерфейс, обозначающий методы REST-запроса
 */

public interface Request {

    String getUri();

    HttpMethods getMethod();

    Map<String, String> getParameters();

    Map<String, String> getHeaders();

    Object getBody();
}
