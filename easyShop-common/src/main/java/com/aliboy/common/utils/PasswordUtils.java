package com.aliboy.common.utils;

import java.util.UUID;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2020-01-02 18:33
 */
public class PasswordUtils {
    private static final String LOWERCASE = ".*[a-z]{1,}.*";
    private static final String UPPERCASE = ".*[A-Z]{1,}.*";
    private static final String CHAR = ".*[a-zA-Z]{1,}.*";
    private static final String NUMBER = ".*\\d{1,}.*";
    private static final String SPECIAL = ".*[~!@#$%^+,()&*/_\\\\.?]{1,}.*";
    private static final String COMPLEX = ".*((?=[\\x21-\\x7e]+)[^A-Za-z0-9]).*";
    private static final int MIN_LEN = 6;
    private static final int MAX_LEN = 18;
    private static final int MIN_COMPLEX = 3;
    private static final int MIN_COMPLEX_TWO = 2;

    public PasswordUtils() {
    }

    public static String generateSalt() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        return uuid.substring(5, 11);
    }

    public static String randomPassword() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        return uuid.substring(5, 13);
    }

    public static String encryptPassword(String plainText, String salt) {
        return MD5HashUtil.getMD5Hash(plainText, salt, 1);
    }
}
