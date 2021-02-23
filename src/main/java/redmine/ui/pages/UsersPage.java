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
    @FindBy(xpath = "//a[@class='sort asc icon icon-sorted-desc']")
    private WebElement sortingUsersByLogin;


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
