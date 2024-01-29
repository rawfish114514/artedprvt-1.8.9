package com.artedprvt.core.localization;

import com.electronwill.toml.Toml;
import com.artedprvt.Artedprvt;
import com.artedprvt.core.Environment;
import com.artedprvt.core.UserOptions;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class Localization {
    private static String language;
    private static String lastLanguage = "";

    public static String getLanguage() {
        return language;
    }

    public static boolean isChange() {
        return !language.equals(lastLanguage);
    }

    public static synchronized void load() {
        language = String.valueOf(UserOptions.options.get("language"));
        if (isChange()) {
            translation = new HashMap<>();
            try {
                String path = "/language/" + language + "/list.txt";
                InputStream input = Artedprvt.class.getResourceAsStream(path);
                StringBuilder sb = new StringBuilder();
                int n = 0;
                while ((n = input.read()) != -1) {
                    sb.append((char) n);
                }
                String list = sb.toString();
                String[] array = list.split("\n");
                for (String v : array) {
                    int cindex = v.indexOf(';');
                    String type = v.substring(0, cindex);
                    String value = v.substring(cindex + 1);
                    if (type.equals("put")) {
                        //将翻译键都添加到 translation 中
                        path = "/language/" + language + "/" + value;
                        path = path.replace("\r", "");
                        InputStream input0 = Artedprvt.class.getResourceAsStream(path);
                        Reader reader = new InputStreamReader(input0, StandardCharsets.UTF_8);
                        sb = new StringBuilder();
                        while ((n = reader.read()) != -1) {
                            sb.append((char) n);
                        }
                        String str = sb.toString();
                        Map<String, String> table = parse(str);
                        translation.putAll(table);
                    } else {
                        //将翻译键设置为 Translatable 静态成员
                        path = "/language/" + language + "/" + value;
                        path = path.replace("\r", "");
                        InputStream input0 = Artedprvt.class.getResourceAsStream(path);
                        Reader reader = new InputStreamReader(input0, StandardCharsets.UTF_8);
                        sb = new StringBuilder();
                        while ((n = reader.read()) != -1) {
                            sb.append((char) n);
                        }
                        String str = sb.toString();
                        Map<String, String> table = parse(str);

                        Class<?> clas = Class.forName(type);
                        if (Translatable.class.isAssignableFrom(clas)) {
                            for (String key : table.keySet()) {
                                String trans = table.get(key);
                                clas.getField(key).set(clas, trans);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(System.err);

            }
        }
        lastLanguage = language;
    }

    private static Map<String, String> parse(String str) {
        Map<String, Object> result = Toml.read(str);
        Map<String, String> result0 = new HashMap<>();
        for (Map.Entry<String, Object> entry : result.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String stringValue = String.valueOf(value);
            result0.put(key, stringValue);
        }
        return result0;
    }

    private static Map<String, String> translation;

    public static String getTranslate(String key, Object... args) {
        String value = translation.get(key);
        if (value == null) {
            Environment.MODLOGGER.info("调用不存在的翻译键: " + key);
            return "";
        }
        return MessageFormat.format(value, args);
    }
}
