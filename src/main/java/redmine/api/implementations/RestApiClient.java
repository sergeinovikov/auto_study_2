package redmine.api.implementations;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.model.user.User;

import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;

/**
 * Класс описывающий API-клиент
 */

public class RestApiClient implements ApiClient {

    private String token;

    public RestApiClient(User user) {
        Objects.requireNonNull(user, "Пользователь должен быть инициализирован");
        Objects.requireNonNull(user.getApiToken().getValue(), "У пользователя должен быть ключ API");
        this.token = user.getApiToken().getValue();
    }

    /**
     * Выполняет SQL - запрос и возвращает результат
     *
     * @param request - REST-запрос
     * @return данные - REST-ответ
     */

    @Override
    @Step("Выполнение Rest-запроса")
    public Response executeRequest(Request request) {
        RequestSpecification specification = given();
        Map<String, String> headers = request.getHeaders();
        headers.put("X-Redmine-API-Key", token);
        if (headers.get("Content-Type") == null) {
            specification.contentType(ContentType.JSON);
        }
        specification.headers(headers)
                .baseUri(request.getUri())
                .queryParams(request.getParameters());
        if (request.getBody() != null) {
            specification.body(request.getBody().toString());
        }
        Method method = Method.valueOf(request.getMethod().name());

        io.restassured.response.Response response = specification.log().all().request(method);
        response.then().log().all();
        Response restResponse = new RestResponse(response);
        addAttachments(request, restResponse);
        return restResponse;
    }

    /**
     * Добавление файлов запроса и ответа в отчёт Allure
     */

    private void addAttachments(Request request, Response response) {
        Allure.addAttachment("Зарпос", request.toString());
        Allure.addAttachment("Ответ", response.toString());
    }

    @Override
    public String toString() {
        return "RestApiClient{" +
                "token='" + token + '\'' +
                '}';
    }
}
