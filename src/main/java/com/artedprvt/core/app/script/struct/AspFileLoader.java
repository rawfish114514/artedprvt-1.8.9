package com.artedprvt.core.app.script.struct;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * asp文件加载器
 */
public class AspFileLoader implements FileLoader {
    public Map<String, byte[]> map;

    public AspFileLoader(String asp) throws Exception {
        map = new HashMap<>();
        readEntry(new FileInputStream(asp), map);
    }

    public AspFileLoader(InputStream inputStream) throws Exception {
        map = new HashMap<>();
        readEntry(inputStream, map);
    }

    public void readEntry(InputStream inputStream, Map<String, byte[]> map) throws Exception {
        ZipInputStream zip = new ZipInputStream(
                inputStream,
                Charset.forName("cp437"));

        ZipEntry entry = null;
        while ((entry = zip.getNextEntry()) != null) {
            if (!entry.isDirectory()) {
                int n;
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                while ((n = zip.read()) != -1) {
                    outputStream.write(n);
                }
                map.put(dediff(entry.getName()), outputStream.toByteArray());
            }
        }
        zip.close();
    }

    @Override
    public String getContent(String path) {
        byte[] bs = map.get(path);
        if (bs == null) {
            return null;
        }
        return new String(bs, StandardCharsets.UTF_8);
    }

    @Override
    public InputStream getInputStream(String path) {
        byte[] bs = map.get(path);
        if (bs == null) {
            return null;
        }
        return new ByteArrayInputStream(bs);
    }

    @Override
    public boolean isExists(String path) {
        return map.containsKey(path);
    }

    @Override
    public List<String> getPaths() {
        return new ArrayList<>(map.keySet());
    }

    public String dediff(String s) {
        return s.replace("\\", "/");
    }
}