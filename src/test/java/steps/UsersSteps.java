package steps;

import cucumber.api.java.ru.И;
import org.openqa.selenium.WebElement;
import redmine.model.user.User;
import redmine.ui.pages.UsersPage;
import redmine.ui.pages.helpers.CucumberPageObjectHelper;
import redmine.utils.Asserts;

import java.util.List;

import static redmine.ui.pages.helpers.Pages.getPage;

public class UsersSteps {

    @И("Таблица пользователей отсортирована по столбцу {string} по возрастанию - не учитывается регистр")
    public void userDataIsSortedAsc(String columnName) {
        List<WebElement> usersData = CucumberPageObjectHelper.getListOfElementBy("Пользователи", columnName);

        Asserts.assertTrue(
                getPage(UsersPage.class).usersSortedAsc(usersData)
        );
    }

    @И("Таблица пользователей отсортирована по столбцу {string} по убыванию - не учитывается регистр")
    public void usersDataIsSortedDesc(String columnName) {
        List<WebElement> usersData = CucumberPageObjectHelper.getListOfElementBy("Пользователи", columnName);

        Asserts.assertTrue(
                getPage(UsersPage.class).usersSortedDesc(usersData)
        );
    }

    @И("Таблица пользователей не отсортирована по столбцу {string} по возрастанию")
    public void userDataNotSortedAsc(String columnName) {
        List<WebElement> usersData = CucumberPageObjectHelper.getListOfElementBy("Пользователи", columnName);

        Asserts.assertFalse(
                getPage(UsersPage.class).usersSortedAsc(usersData)
        );
    }

    @И("Таблица пользователей не отсортирована по столбцу {string} по убыванию")
    public void usersDataNotSortedDesc(String columnName) {
        List<WebElement> usersData = CucumberPageObjectHelper.getListOfElementBy("Пользователи", columnName);

        Asserts.assertFalse(
                getPage(UsersPage.class).usersSortedDesc(usersData)
        );
    }

}
