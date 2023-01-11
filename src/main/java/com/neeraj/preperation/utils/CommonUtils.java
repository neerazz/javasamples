package com.neeraj.preperation.utils;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CommonUtils {
    private CommonUtils() {
    }

    public static String getSHA256Hash(Object... inputs) {
        try {
            StringBuilder sb = new StringBuilder();
            for (Object input : inputs) {
                if (input == null) continue;
                sb.append(input);
            }
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            var bytes = sb.toString().getBytes(StandardCharsets.UTF_8);
            var encodedHash = digest.digest(bytes);
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
