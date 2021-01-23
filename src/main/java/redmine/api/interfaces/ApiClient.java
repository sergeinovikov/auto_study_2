package redmine.api.interfaces;

/**
 * Класс-интерфейс, обозначающий методы API-клиента
 */

public interface ApiClient {

    Response executeRequest(Request request);
}
