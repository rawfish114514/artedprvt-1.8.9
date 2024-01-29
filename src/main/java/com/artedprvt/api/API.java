package com.artedprvt.api;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class API {
    static Map<String,byte[]> map=new HashMap<>();

    public static void init() {
        try {
            read(API.class.getResourceAsStream("/artedprvt.jar"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    static void read(InputStream jar) throws IOException {
        if(jar==null){
            return;
        }
        ZipInputStream zip = new ZipInputStream(
                jar,
                Charset.forName("cp437"));

        ZipEntry entry = null;
        while ((entry = zip.getNextEntry()) != null) {
            if (!entry.isDirectory()) {
                int n;
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                while ((n = zip.read()) != -1) {
                    outputStream.write(n);
                }
                map.put(entry.getName().replace(File.separatorChar, '/'), outputStream.toByteArray());
            }
        }
        zip.close();
    }
}
