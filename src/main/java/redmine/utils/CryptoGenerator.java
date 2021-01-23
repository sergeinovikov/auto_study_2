package redmine.utils;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

/**
 * Класс описывающий генераторы HEX строки и зашифрованного пароля
 */

public class CryptoGenerator {
    private static final String DIGITS = "1234567890";
    private static final String LETTERS_FOR_HEX = "abcdef";

    public static String generateHEX(int lenght) {
        return StringGenerators.randomString(32, DIGITS + LETTERS_FOR_HEX);
    }

    public static String generatePassword(String salt) {
        String generatedPassword = StringGenerators.randomString(8, StringGenerators.ENGLISH_LOWER + DIGITS);
        String transitPassword = Hashing.sha1()
                .hashString(generatedPassword, StandardCharsets.UTF_8)
                .toString();
        return Hashing.sha1()
                .hashString(salt + transitPassword, StandardCharsets.UTF_8)
                .toString();
    }
}
