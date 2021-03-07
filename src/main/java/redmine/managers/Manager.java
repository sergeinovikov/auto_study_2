package redmine.managers;

import com.google.common.collect.ImmutableMap;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import redmine.Property;
import redmine.db.DataBaseConnection;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Менеджер для хранения сущностей, необходимых для взаимодействия с тестируемой системой
 */

public class Manager {
    public final static DataBaseConnection dbConnection = new DataBaseConnection();
    private static WebDriver driver;
    private static WebDriverWait wait;

    /**
     * Получить экземляр драйвера (ленивая инициализация)
     *
     * @return драйвер
     */

    public static WebDriver driver() {
        if (driver == null) {
            driver = getPropertiesDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Property.getIntegerProperty("ui.implicitly.wait"), TimeUnit.SECONDS);
            wait = new WebDriverWait(driver, Property.getIntegerProperty("ui.condition.wait"));
        }
        return driver;
    }

    /**
     * Закрыть браузер, отчистить драйвер
     */

    public static void driverQuit() {
        if (driver != null) {
            driver.quit();
        }
        driver = null;
    }

    /**
     * Открыть страницу Redmine
     */

    @Step("Открыть страницу {0}")
    public static void openPage(String uri) {
        driver().get(Property.getStringProperty("ui.url") + uri);
    }

    /**
     * Сделать скриншот
     */

    @Attachment(value = "screenshot")
    public static byte[] takesScreenshot() {
        return ((TakesScreenshot) driver()).getScreenshotAs(OutputType.BYTES);
    }


    /**
     * Возвращает экземпляр драйвера в зависимости от значения в properties-файле
     *
     * @return драйвер
     */

    @SneakyThrows
    public static WebDriver getPropertiesDriver() {
        if (Property.getBooleanProperty("remote")) {
            MutableCapabilities capabilities = new ChromeOptions();
            capabilities.setCapability("browserName", Property.getStringProperty("browser"));
            capabilities.setCapability("browserVersion", Property.getStringProperty("browser.version"));
            Map<String, Object> selenoidOptions = ImmutableMap.of(
                    "enableVNC", Property.getBooleanProperty("enable.vnc"),
                    "enableVideo", Property.getBooleanProperty("enable.video")
            );
            capabilities.setCapability("selenoid:options", selenoidOptions);
            return new RemoteWebDriver(
                    new URL(Property.getStringProperty("selenoid.hub.url")),
                    capabilities
            );

        } else {
            switch (Property.getStringProperty("browser")) {
                case "chrome":
                    System.setProperty("webdriver.chrome.driver", Property.getStringProperty("webdriver.chrome.driver"));
                    return new ChromeDriver();
                case "firefox":
                    System.setProperty("webdriver.gecko.driver", Property.getStringProperty("webdriver.gecko.driver"));
                    return new FirefoxDriver();
                default:
                    throw new IllegalArgumentException("Неизвестный тип браузера");
            }
        }
    }
}
