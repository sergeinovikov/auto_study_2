package redmine.utils;

import java.util.Random;

/**
 * Класс генераторов текстовых данных
 */

public class StringGenerators {
    public static final String ENGLISH_UPPER = "QWERTYUIOPASDFGHJKLZXCVBNM";
    public static final String ENGLISH_LOWER = ENGLISH_UPPER.toLowerCase();
    public static final String ENGLISH = ENGLISH_UPPER + ENGLISH_LOWER;
    public static final String DIGITS_CHARACTERS = "1234567890!@#$%^&*()";


    public static String randomEnglishString(int length) {
        return randomString(length, ENGLISH);
    }

    public static String randomEnglishLowerString(int length) {
        return randomString(length, ENGLISH_LOWER);
    }

    public static String randomEmail() {
        return randomEnglishLowerString(8) + "@" + randomEnglishLowerString(8) + "." + randomEnglishLowerString(3);
    }

    public static String randomString(int length, String pattern) {
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            randomString.append(pattern.charAt(new Random().nextInt(pattern.length())));
        }
        return randomString.toString();
    }

    public static void main(String[] args) {
        System.out.println(randomEmail());
    }
}
