package steps;

import cucumber.api.java.ru.И;
import redmine.api.implementations.RestApiClient;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.managers.Context;

public class ResponseSteps {

    @И("По запросу {string} через API-клиент {string} получить ответ {string}")
    public void getResponse(String requestStashId, String apiClientStashId, String responseStashId) {
        Request request = Context.get(requestStashId, Request.class);
        ApiClient apiClient = Context.get(apiClientStashId, RestApiClient.class);
        Response response = apiClient.executeRequest(request);
        Context.put(responseStashId, response);
    }
}
