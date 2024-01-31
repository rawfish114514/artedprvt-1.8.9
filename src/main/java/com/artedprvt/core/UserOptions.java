package com.artedprvt.core;

import com.artedprvt.core.localization.Localization;
import com.electronwill.toml.Toml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserOptions {
    private static String path = System.getProperties().get("user.dir") + "/.artedprvt/options.toml";
    public static Map<String, Object> options;

    public static List<String> keyList = new ArrayList<String>() {{
        add("language");
        add("repository");
    }};

    public static Map<String, String> defaultMap = new HashMap<String, String>() {{
        put("language", "zh_cn");
        put("repository", "https://gitee.com/rawfishc/apse/raw/master/");
    }};
    public static Map<String, List<String>> valuesMap = new HashMap<String, List<String>>() {{
        put("language", s("zh_cn"));
        put("repository", s("https://gitee.com/rawfishc/apse/raw/master/"));
    }};

    public static List<String> s(String... strs) {
        List<String> list = new ArrayList<>();
        for (String s : strs) {
            list.add(s);
        }
        return list;
    }

    /**
     * 返回有效值
     *
     * @param key
     * @return
     */
    public static List<String> getValues(String key) {
        return valuesMap.get(key);
    }

    public static void setValue(String key, Object value) {
        options.put(key, value);
    }

    public static Object getValue(String key) {
        return options.get(key);
    }

    public static synchronized void load() {
        try {
            if (new File(path).isFile()) {
                //从文件加载配置
                loadFromFile();
            } else {
                //加载默认配置并写入文件
                loadDefault();
                writeToFile();
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        options.forEach((k, v) -> {
            if (!keyList.contains(k)) options.remove(k);
        });
        Localization.load();
    }

    public static void loadDefault() {
        options = new HashMap<>(defaultMap);
    }

    public static void loadFromFile() throws Exception {
        options = new HashMap<>();
        File file = new File(path);
        Reader reader = new InputStreamReader(new FileInputStream(file));
        StringBuilder sb = new StringBuilder();
        int n;
        while ((n = reader.read()) != -1) {
            sb.append((char) n);
        }
        options.putAll(Toml.read(sb.toString()));
    }

    public static void writeToFile() throws Exception {
        File file = new File(path);
        if (!file.isFile()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        String string = Toml.writeToString(options);
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        writer.write(string);
        writer.flush();
        writer.close();
    }
}