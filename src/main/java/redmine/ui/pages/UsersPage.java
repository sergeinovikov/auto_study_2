package redmine.ui.pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UsersPage extends AbstractPage {
    @FindBy(xpath = "//table[@class='list users']")
    private WebElement usersTable;
    @FindBy(xpath = "//td[@class='username']")
    private List<WebElement> usersLogins;
    @FindBy(xpath = "//td[@class='firstname']")
    private List<WebElement> usersFirstNames;
    @FindBy(xpath = "//td[@class='lastname']")
    private List<WebElement> usersLastNames;
    @FindBy(xpath = "//a[text()[contains(.,'Пользователь')]]")
    private WebElement sortingUsersByLogin;
    @FindBy(xpath = "//a[text()[contains(.,'Имя')]]")
    private WebElement sortingUsersByFirstName;
    @FindBy(xpath = "//a[text()[contains(.,'Фамилия')]]")
    private WebElement sortingUsersByLastName;
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
