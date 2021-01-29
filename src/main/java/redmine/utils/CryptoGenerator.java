package redmine.utils;

import static org.apache.commons.codec.digest.DigestUtils.*;

/**
 * Класс описывающий генераторы HEX строки и зашифрованного пароля
 */

public class CryptoGenerator {
    private static final String DIGITS = "1234567890";
    private static final String LETTERS_FOR_HEX = "abcdef";

    public static String generateHEX(int length) {
        return StringGenerators.randomString(length, DIGITS + LETTERS_FOR_HEX);
    }

    public static String generatePassword(String salt) {
        String generatedPassword = StringGenerators.randomString(8, StringGenerators.ENGLISH_LOWER + DIGITS);
        String transitPassword = sha1Hex(generatedPassword);

        return sha1Hex((salt + transitPassword));
    }
}
