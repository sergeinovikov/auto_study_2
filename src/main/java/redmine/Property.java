package redmine;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Property {
    public static Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream("src/test/java/resources/local.properties"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static String getStringProperty(String key) {
        return properties.getProperty(key);
    }

    public static Integer getIntegerProperty(String key) {
        return Integer.parseInt(getStringProperty(key));
    }

    public static Boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getStringProperty(key));
    }
}
