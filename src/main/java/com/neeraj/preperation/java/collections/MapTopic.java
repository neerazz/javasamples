package com.neeraj.preperation.java.collections;

import java.util.*;

public class MapTopic {
    public static void main(String[] args) {
        Map<String, String> map1 = new HashMap<>();
        Map<String, String> map2 = Collections.synchronizedMap(map1);
        Set<String> names = new TreeSet<>((n1, n2) -> n2.compareToIgnoreCase(n1));
        names.add("1");
        names.add("2");
        System.out.println("names = " + names);

    }
}
