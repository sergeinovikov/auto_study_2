package redmine.ui.pages;

import lombok.SneakyThrows;
import org.openqa.selenium.support.PageFactory;

import static redmine.managers.Manager.driver;

/**
 * Класс для полученя PageObject с инициализированными объектами
 */

public class Pages {

    @SneakyThrows
    public static <T extends AbstractPage> T getPage(Class<T> clazz) {
        T page = clazz.newInstance();
        PageFactory.initElements(driver(), page);
        return page;
    }
}
