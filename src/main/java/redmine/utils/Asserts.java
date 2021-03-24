package redmine.utils;

import org.testng.Assert;

import io.qameta.allure.Step;

/**
 * Вспомогательный класс описывающий обёртки Assert для отчёта в Allure
 */

public class Asserts {

    @Step("Сравнение переменных на равенство actual: {0}, expected: {1}")
    public static void assertEquals(Object actual, Object expected) {
        Assert.assertEquals(actual, expected);
    }

    @Step("Сравнение переменных на неравенство actual: {0}, not expected: {1}")
    public static void assertNotEquals(Object actual, Object expected) {
        Assert.assertNotEquals(actual, expected);
    }

    @Step("Сравнение Boolean переменной {0}")
    public static void assertTrue(Boolean actual, String message) {
        Assert.assertTrue(actual, message);
    }

    @Step("Сравнение Boolean переменной {0} ")
    public static void assertFalse(Boolean actual, String message) {
        Assert.assertFalse(actual, message);
    }

    @Step("Проверка переменной {0} на NotNull")
    public static void assertNotNull(Object actual, String message) {
        Assert.assertNotNull(actual, message);
    }

    @Step("Проверка переменной {0} на Null")
    public static void assertNull(Object actual, String message) {
        Assert.assertNull(actual, message);
    }
}