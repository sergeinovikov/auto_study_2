package redmine.ui.pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.ui.pages.helpers.CucumberName;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@CucumberName("Пользователи")
public class UsersPage extends AbstractPage {

    @CucumberName("Список пользователей")
    @FindBy(xpath = "//table[@class='list users']")
    private WebElement usersTable;

    @CucumberName("Список логинов")
    @FindBy(xpath = "//td[@class='username']")
    private List<WebElement> usersLogins;

    @CucumberName("Список имён")
    @FindBy(xpath = "//td[@class='firstname']")
    private List<WebElement> usersFirstNames;

    @CucumberName("Список фамилий")
    @FindBy(xpath = "//td[@class='lastname']")
    private List<WebElement> usersLastNames;

    @CucumberName("Сортировать по логину")
    @FindBy(xpath = "//a[text()[contains(.,'Пользователь')]]")
    private WebElement sortingUsersByLogin;

    @CucumberName("Сортировать по имени")
    @FindBy(xpath = "//a[text()[contains(.,'Имя')]]")
    private WebElement sortingUsersByFirstName;

    @CucumberName("Сортировать по фамилии")
    @FindBy(xpath = "//a[text()[contains(.,'Фамилия')]]")
    private WebElement sortingUsersByLastName;

    @CucumberName("Создать пользователя")
    @FindBy(xpath = "//a[@href='/users/new']")
    private WebElement newUser;


    public Boolean usersSortedAsc(List<WebElement> usersData) {
        List<String> usersDataDefaultSorting = usersData.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        List<String> usersDataSortedDesc = usersData.stream()
                .map(WebElement::getText)
                .sorted(Comparator.comparing(String::toString, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());

        return usersDataDefaultSorting.equals(usersDataSortedDesc);
    }

    public Boolean usersSortedDesc(List<WebElement> usersData) {
        List<String> usersDataDefaultSorting = usersData.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        List<String> usersDataSortedDesc = usersData.stream()
                .map(WebElement::getText)
                .sorted(Comparator.comparing(String::toString, String.CASE_INSENSITIVE_ORDER.reversed()))
                .collect(Collectors.toList());

        return usersDataDefaultSorting.equals(usersDataSortedDesc);
    }
}
