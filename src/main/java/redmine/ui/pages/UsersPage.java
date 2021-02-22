package redmine.ui.pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UsersPage extends AbstractPage {
    @FindBy(xpath = "//table[@class='list users']")
    private WebElement usersTable;
    @FindBy(xpath = "//td[@class='username']")
    private List<WebElement> usersFromTable;
    @FindBy(xpath = "//a[@class='sort asc icon icon-sorted-desc']")
    private WebElement sortingUsersByLogin;


    public void usersSortedByLoginAsc() {
        List<String> usersLoginsWithDefaultSorting = usersFromTable.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        List<String> usersSortedByLoginAsc = usersFromTable.stream()
                .map(WebElement::getText)
                .sorted(Comparator.comparing(String::toString, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());

        Assert.assertEquals(usersLoginsWithDefaultSorting, usersSortedByLoginAsc);
    }

    public void usersSortedByLoginDesc() {
        List<String> usersLoginsWithDefaultSorting = usersFromTable.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        List<String> usersSortedByLoginDesc = usersFromTable.stream()
                .map(WebElement::getText)
                .sorted(Comparator.comparing(String::toString, String.CASE_INSENSITIVE_ORDER.reversed()))
                .collect(Collectors.toList());

        Assert.assertEquals(usersLoginsWithDefaultSorting, usersSortedByLoginDesc);
    }
}
