package com.neeraj.preperation.utils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectionsUtils {

    private CollectionsUtils() {
    }

    public static <T> T getMaxOccurring(List<T> inputList) {
        if (inputList == null) return null;
        Comparator<Map.Entry<T, Long>> order = (e1, e2) -> Long.compare(e2.getValue(), e1.getValue());
        return inputList.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                .entrySet()
                .stream()
                .max(order)
                .map(Map.Entry::getKey)
                .stream().findFirst().orElse(null);
    }

    public static <T> boolean isEmpty(List<T> inputList) {
        return inputList == null || inputList.isEmpty();
    }

    public static <T> boolean isNotEmpty(List<T> inputList) {
        return !isEmpty(inputList);
    }
}
