package com.aliboy.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2020-01-02 18:34
 */
public class MD5HashUtil {
    private static final String DEFAULT_ALGORITHM = "MD5";
    private static final String DEFAULT_ENCODING = "UTF-8";

    public MD5HashUtil() {
    }

    public static String getMD5Hash(String source) {
        return getHash("MD5", source, (String)null, 1);
    }

    public static String getMD5Hash(String source, String salt, int hashIterations) {
        return getHash("MD5", source, salt, hashIterations);
    }

    public static String getHash(String algorithm, String source, String salt, int hashIterations) {
        byte[] saltBytes = null;
        if (salt != null) {
            saltBytes = toBytes(salt, "UTF-8");
        }

        byte[] sourceBytes = toBytes(source, "UTF-8");
        byte[] hashedBytes = hash(algorithm, sourceBytes, saltBytes, hashIterations);
        return HexUtil.encodeToString(hashedBytes).toUpperCase();
    }

    protected static byte[] hash(String algorithm, byte[] bytes, byte[] salt, int hashIterations) {
        MessageDigest digest = getDigest(algorithm);
        if (salt != null) {
            digest.reset();
            digest.update(salt);
        }

        byte[] hashed = digest.digest(bytes);
        int iterations = hashIterations - 1;

        for(int i = 0; i < iterations; ++i) {
            digest.reset();
            hashed = digest.digest(hashed);
        }

        return hashed;
    }

    private static MessageDigest getDigest(String algorithmName) {
        try {
            return MessageDigest.getInstance(algorithmName);
        } catch (NoSuchAlgorithmException var3) {
            String msg = "No native '" + algorithmName + "' MessageDigest instance available on the current JVM.";
            throw new IllegalArgumentException(msg, var3);
        }
    }

    private static byte[] toBytes(String source, String encoding) {
        try {
            return source.getBytes(encoding);
        } catch (UnsupportedEncodingException var4) {
            String msg = "Unable to convert source [" + source + "] to byte array using encoding '" + encoding + "'";
            throw new RuntimeException(msg, var4);
        }
    }
}
