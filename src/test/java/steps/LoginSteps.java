package steps;

import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.И;
import redmine.managers.Context;
import redmine.managers.Manager;
import redmine.model.user.User;
import redmine.ui.pages.LoginPage;
import redmine.ui.pages.helpers.Pages;

public class LoginSteps {

    @И("Открыт браузер на странице авторизации")
    public void openBrowser() {
        Manager.openPage("login");
    }

    @Если("Авторизоваться пользователем {string}")
    public void userLogin(String userStashId) {
        User user = Context.get(userStashId, User.class);
        Pages.getPage(LoginPage.class).login(
                user.getLogin(), user.getPassword()
        );
    }
}
