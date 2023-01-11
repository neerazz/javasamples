package com.neeraj.preperation.utils;

import com.fasterxml.jackson.databind.JsonNode;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class StringUtils {

    private StringUtils() {
    }

    public static boolean hasValue(String inputString) {
        return !isEmpty(inputString);
    }

    public static boolean hasValue(Collection<String> input) {
        return input != null && !input.isEmpty();
    }

    public static boolean hasValue(JsonNode jsonNode) {
        return jsonNode != null && !jsonNode.isNull();
    }

    public static boolean hasValue(Object inputObj) {
        return Objects.nonNull(inputObj);
    }

    public static boolean isEmpty(String inputString) {
        return inputString == null || inputString.trim().length() == 0;
    }

    /**
     * @param input,         Given an input string
     * @param replaceString, and symbolic string, and the corresponding value.
     * @return Hello, My name is Robo. I can work as Vulnerability Engineer.
     * @implNote <p>
     * Example: <br>
     * input: Hello, My name is ${name}. I can work as ${occupation}. <br>
     * replaceString : Map.of("name", "Robo", "occupation", "Vulnerability Engineer"
     * </p>
     */
    public static String replace(String input, Map<String, String> replaceString) {
        String output = input;
        for (var entry : replaceString.entrySet()) {
            var inputString = String.format("${%s}", entry.getKey());
            output = output.replace(inputString, entry.getValue());
        }
        return output;
    }

    public static String getFormData(Map<String, String> formData) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : formData.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8).replace("\\+", "%20"));
        }
        return result.toString();
    }

    public static String wrapString(String input) {
        return String.format("\"%s\"", input);
    }
}
