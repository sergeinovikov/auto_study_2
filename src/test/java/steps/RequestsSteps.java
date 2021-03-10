package steps;

import cucumber.api.java.bg.И;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.HttpMethods;
import redmine.api.interfaces.Request;
import redmine.managers.Context;
import redmine.model.dto.UserDto;
import redmine.model.dto.UserInfo;
import redmine.utils.StringGenerators;
import redmine.utils.gson.GsonHelper;

import java.util.Map;


public class RequestsSteps {

    @И("Создать POST-запрос на создание пользователя {string} с запросом {string} с параметрами:")
    public void sendPostRequest(String userDtoStashId, String requestStashId, Map<String, String> parameters) {
        String login = "SN" + StringGenerators.randomEnglishLowerString(8);
        String firstName = "Ser" + StringGenerators.randomEnglishString(8);
        String lastName = "Nov" + StringGenerators.randomEnglishString(8);
        String mail = StringGenerators.randomEmail(8);
        String password = StringGenerators.randomPassword(8);
        int status;
        if (parameters.containsKey("Статус")) {
            status = (Integer.parseInt(parameters.get("Статус")));
        } else {
            throw new IllegalArgumentException("Не известен или отсутствует статус пользователя");
        }

        UserDto userForCreation = new UserDto()
                .setUser(new UserInfo()
                        .setLogin(login)
                        .setFirstname(firstName)
                        .setLastname(lastName)
                        .setMail(mail)
                        .setPassword(password)
                        .setStatus(status)
                );
        Context.put(userDtoStashId, userForCreation);

        String body = GsonHelper.getGson().toJson(userForCreation);

        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);

        Context.put(requestStashId, request);
    }
}
