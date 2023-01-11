package com.neeraj.preperation.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncoderDecoder {

    public static String decode(String encoding, String content) {
        byte[] byteContent = content.replace("\\n", "\n").getBytes(StandardCharsets.UTF_8);
        byte[] decode = null;
        if (encoding.equalsIgnoreCase("base64")) {
            decode = Base64.getMimeDecoder().decode(byteContent);
        }
        return new String(decode);
    }
}
