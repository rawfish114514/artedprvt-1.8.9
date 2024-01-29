package com.artedprvt.core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassGroupSystem {
    private static Map<String, ClassGroup> map = new LinkedHashMap<>();

    public static ClassGroup get(String name) {
        return map.get(name);
    }

    private static Pattern pattern = Pattern.compile("(([a-zA-Z_][0-9a-zA-Z_]*\\.)*)([a-zA-Z_][0-9a-zA-Z_]*)");

    public static void add(ClassGroup classGroup) {
        if (classGroup.permission()) {
            String name = classGroup.getName();
            Matcher matcher = pattern.matcher(name);
            if (matcher.matches()) {
                map.put(name, classGroup);
            }
        }
    }

    public static String getValue() {
        Set<String> set = map.keySet();
        return String.join(", ", set);
    }
}
