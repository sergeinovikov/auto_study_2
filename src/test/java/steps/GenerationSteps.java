package steps;

import cucumber.api.java.ru.Пусть;
import redmine.managers.Context;
import redmine.model.user.User;

import java.util.Map;

public class GenerationSteps {

    @Пусть("Существует пользователь {string}")
    public void userGeneration (String userStashId, Map<String, String> params) {
        User user = new User();
        if (params.containsKey("admin")) {
            user.setAdmin(Boolean.parseBoolean(params.get("admin")));
        }
        if (params.containsKey("status")) {
            user.setStatus(Integer.parseInt(params.get("status")));
        }
        user.generate();
        Context.getStash().put(userStashId, user);
    }
}
