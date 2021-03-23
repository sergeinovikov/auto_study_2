package redmine.utils;

import java.util.Random;

import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;

/**
 * Класс описывающий генераторы текстовых данных
 */

public class StringGenerators {
    private static final String ENGLISH_UPPER = "QWERTYUIOPASDFGHJKLZXCVBNM";
    private static final String ENGLISH_LOWER = ENGLISH_UPPER.toLowerCase();
    private static final String ENGLISH = ENGLISH_UPPER + ENGLISH_LOWER;
    private static final String DIGITS = "1234567890";
    private static final String CHARACTERS = "!@#$%^&*()";
    private static final String LETTERS_FOR_HEX = "abcdef";


    public static String randomEnglishString(int length) {
        return randomString(length, ENGLISH);
    }

    public static String randomEnglishLowerString(int length) {
        return randomString(length, ENGLISH_LOWER);
    }

    public static String randomEmail(int length) {
        return randomEnglishLowerString(length) + "@" + randomEnglishLowerString(length / 2) + "." + randomEnglishLowerString(length / 4);
    }

    public static String randomPassword(int length) {
        return randomString(length, ENGLISH + DIGITS + CHARACTERS);
    }

    public static String generateHexString(int length) {
        return StringGenerators.randomString(length, LETTERS_FOR_HEX + DIGITS);
    }

    public static String generateHashPassword(String salt, String password) {
        return sha1Hex((salt + sha1Hex(password)));
    }

    public static String randomString(int length, String pattern) {
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            randomString.append(pattern.charAt(new Random().nextInt(pattern.length())));
        }
        return randomString.toString();
    }
}
