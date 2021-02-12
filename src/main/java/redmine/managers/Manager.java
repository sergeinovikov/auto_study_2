package redmine.managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import redmine.Property;
import redmine.db.DataBaseConnection;

/**
 * Менеджер для хранения сущностей, необходимых для взаимодействия с тестируемой системой
 */

public class Manager {
    public final static DataBaseConnection dbConnection = new DataBaseConnection();
    private static WebDriver driver;

    /**
     * Получить экземляр драйвера (ленивая инициализация)
     *
     * @return драйвер
     */

    public static WebDriver driver() {
        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", Property.getStringProperty("webdriver.chrome.driver"));
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
        return driver;
    }

    /**
     * Закрыть браузер, отчистить драйвер
     */

    public static void driverQuit() {
        driver.quit();
        driver = null;
    }

    /**
     * Открыть Redmine
     */

    public static void openPage(String uri) {
       driver().get(Property.getStringProperty("ui.url") + uri);
    }
}
