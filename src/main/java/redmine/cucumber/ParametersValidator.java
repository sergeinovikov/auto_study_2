package redmine.cucumber;

import lombok.SneakyThrows;
import org.testng.Assert;
import redmine.managers.Context;
import redmine.ui.pages.helpers.CucumberName;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ParametersValidator {

    public static void validateRoleParameters(Map<String, String> parameters) {
        parameters.forEach((key, value) -> Assert.assertTrue(
                AllowedParameters.ROLE_PARAMETERS.contains(key),
                "Список допустимых параметров при работе с ролями не содержит параметр " + key
        ));
    }

    public static void validateUserParameters(Map<String, String> parameters) {
        parameters.forEach((key, value) -> Assert.assertTrue(
                AllowedParameters.USER_PARAMETERS.contains(key),
                "Список допустимых параметров при пользователями с ролями не содержит параметр " + key
        ));
    }

    public static void validateProjectParameters(Map<String, String> parameters) {
        parameters.forEach((key, value) -> Assert.assertTrue(
                AllowedParameters.PROJECT_PARAMETERS.contains(key),
                "Список допустимых параметров при работе с проектами не содержит параметр " + key
        ));
    }

    public static void validateApiErrorsParameters(List<String> parameters) {
        parameters.forEach(value -> Assert.assertTrue(
                AllowedParameters.ERRORS_PARAMETERS.contains(value),
                "Список допустимых параметров при работе с API-ошибками не содержит параметр " + value
        ));
    }

    @SneakyThrows
    public static String replaceCucumberVariables(String rawString) {
        while (rawString.contains("${")) {
            String replacement = rawString.substring(rawString.indexOf("${"), rawString.indexOf("}") + 1);
            String stashId = replacement.substring(2, replacement.indexOf("->"));
            String fieldDescription = replacement.substring(replacement.indexOf("->") + 2, replacement.length() - 1);

            Object stashObject = Context.get(stashId.trim());

            Field foundFiend = Arrays.stream(stashObject.getClass().getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(CucumberName.class))
                    .filter(field -> field.getAnnotation(CucumberName.class).value().equals(fieldDescription.trim()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(String.format("В классе %s не задана аннотация @CucumberName: %s", stashId.trim(), fieldDescription.trim())));
            foundFiend.setAccessible(true);

            String result = foundFiend.get(stashObject).toString();

            rawString = rawString.replace(replacement, result);
        }
        return rawString;
    }
}
