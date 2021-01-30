package redmine.utils;

import static org.apache.commons.codec.digest.DigestUtils.*;

/**
 * Класс описывающий генераторы HEX строки и зашифрованного пароля
 */

public class CryptoGenerator {
    private static final String LETTERS_FOR_HEX = "abcdef";

    public static String generateHEX(int length) {
        return StringGenerators.randomString(length, LETTERS_FOR_HEX + StringGenerators.DIGITS);
    }

    public static String generatePassword(String salt, String password) {
        return sha1Hex((salt + password));
    }
}
