package com.artedprvt.core.app.java;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class JarClassLoader extends ClassLoader {
    private Map<String, byte[]> jarMap;
    private List<String> loadedClasses;

    public JarClassLoader(InputStream input) throws Exception {
        super(JarClassLoader.class.getClassLoader());
        jarMap = new HashMap<>();
        loadedClasses = new ArrayList<>();


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
                jarMap.put(entry.getName().replace(File.separatorChar, '/'), outputStream.toByteArray());
            }
        }
        zip.close();
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (loadedClasses.contains(name)) {
            return findClass(name);
        }
        byte[] bytes = jarMap.get(name.replace('.', '/') + ".class");
        if (bytes != null) {
            loadedClasses.add(name);
            return defineClass(name, bytes, 0, bytes.length);
        }
        return super.loadClass(name);
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        byte[] bytes = jarMap.get(name);
        if (bytes != null) {
            return new ByteArrayInputStream(bytes);
        }
        return null;
    }
}
