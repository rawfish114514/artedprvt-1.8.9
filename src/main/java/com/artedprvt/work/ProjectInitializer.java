package com.artedprvt.work;

import com.electronwill.toml.Toml;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 项目初始化器
 * 从zip文件输入流中读取
 * 包含了初始化器的所有信息
 * <p>
 * zip文件组成
 * init.toml:项目初始化器配置文件
 * *.java:项目初始化器源码
 * 其他文件
 */
public class ProjectInitializer {
    public static final Map<String, ProjectInitializer> initializerMap = new HashMap<String, ProjectInitializer>() {{
        try {
            put("java", new ProjectInitializer(ProjectInitializer.class.getResourceAsStream("/initializers/initializer-java.zip")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }};

    private Map<String, byte[]> map = new HashMap<String, byte[]>();

    private ProjectInitializer() {
    }

    /**
     * 创建项目初始化器
     *
     * @param input 数据源
     */
    public ProjectInitializer(InputStream input) throws Exception {
        ZipInputStream zip = new ZipInputStream(
                input,
                Charset.forName("cp437"));

        ZipEntry entry = null;
        while ((entry = zip.getNextEntry()) != null) {
            if (!entry.isDirectory()) {
                int n;
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                while ((n = zip.read()) != -1) {
                    outputStream.write(n);
                }
                map.put(entry.getName().replace(File.separator, "/"), outputStream.toByteArray());
            }
        }
        zip.close();

        load();
    }

    private Map<String, byte[]> sourceMap = new HashMap<>();

    private byte[] init;

    private String description="";

    /**
     * 加载
     */
    private void load() {
        if (!map.containsKey("init.toml")) {
            throw new RuntimeException("必要文件缺失: init.toml");
        }
        init = map.remove("init.toml");
        Map<String,Object> initData= Toml.read(new String(init, StandardCharsets.UTF_8));
        if(initData.containsKey("description")){
            description=initData.get("description").toString();
        }
        if(!initData.containsKey("main-class")){
            throw new RuntimeException("必要键缺失: main-class");
        }
        for (String name : new ArrayList<>(map.keySet())) {
            if (name.endsWith(".java")) {
                sourceMap.put(name, map.remove(name));
            }
        }

    }

    /**
     * 初始化项目
     *
     * @param project 项目
     * @return
     */
    public void initialize(Project project) throws IOException {
        for (String name : map.keySet()) {
            writeToFile(new File(project.getTarget(), name), map.get(name));
        }
        File script = new File(project.getTarget(), ".apf/script");
        writeToFile(new File(script, "init.toml"), init);
        File java = new File(script, "java");
        for (String name : sourceMap.keySet()) {
            writeToFile(new File(java, name), sourceMap.get(name));
        }
    }

    public String getDescription() {
        return description;
    }

    private void writeToFile(File file, byte[] bytes) throws IOException {
        if (!file.isFile() && !file.isDirectory()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        OutputStream out = new FileOutputStream(file);
        out.write(bytes);
        out.close();
    }
}
