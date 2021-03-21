package redmine.ui.pages.helpers;

import lombok.SneakyThrows;
import org.openqa.selenium.WebElement;
import org.reflections.Reflections;
import redmine.ui.pages.AbstractPage;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class CucumberPageObjectHelper {

    @SneakyThrows
    public static WebElement getElementBy(String cucumberPageName, String cucumberFieldName) {
        AbstractPage page = getPageBy(cucumberPageName);
        Field foundField = Stream.of(page.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(CucumberName.class))
                .filter(field -> cucumberFieldName.equals(field.getAnnotation(CucumberName.class).value()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        String.format("Нет аннотации @CucumberName(\"%s\") у поля", cucumberFieldName)));
        foundField.setAccessible(true);
        return (WebElement) foundField.get(page);
    }

    @SneakyThrows
    public static List<WebElement> getListOfElementBy(String cucumberPageName, String cucumberFieldName) {
        AbstractPage page = getPageBy(cucumberPageName);
        Field foundField = Stream.of(page.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(CucumberName.class))
                .filter(field -> cucumberFieldName.equals(field.getAnnotation(CucumberName.class).value()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        String.format("Нет аннотации @CucumberName(\"%s\") у поля", cucumberFieldName)));
        foundField.setAccessible(true);
        try {
            return (List<WebElement>) foundField.get(page);
        } catch (ClassCastException exception) {
            throw new ClassCastException("Полученное поле не соответствует типу List<WebElement" + exception);
        }
        //return (List<WebElement>) foundField.get(page);
    }


    @SneakyThrows
    private static AbstractPage getPageBy(String cucumberPageName) {
        Reflections reflections = new Reflections("redmine.ui.pages");
        Set<Class<?>> allClasses = reflections.getTypesAnnotatedWith(CucumberName.class);

        Class<?> pageClass = allClasses.stream()
                .filter(clazz -> cucumberPageName.equals(clazz.getAnnotation(CucumberName.class).value()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        String.format("Нет аннотации @CucumberName(\"%s\") у класса", cucumberPageName)
                ));
        try {
            return Pages.getPage((Class<AbstractPage>) pageClass);
        } catch (ClassCastException exception) {
            throw new ClassCastException("Полученный класс не является наследником AbstractPage" + exception);
        }
        //return Pages.getPage((Class<AbstractPage>) pageClass);
    }

}