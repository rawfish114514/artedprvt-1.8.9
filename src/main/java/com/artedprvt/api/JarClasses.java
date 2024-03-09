package com.artedprvt.api;

import com.artedprvt.api.APIJavaFileObjects.JFO;

import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class JarClasses {
    // fullClassName
    private Map<String,byte[]> map=new HashMap<>();
    public JarClasses(InputStream jar) throws IOException {

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
                String name=entry.getName();
                if(name.endsWith(".class")){
                    map.put(entry.getName().replace(File.separator, ".").replace('/','.').substring(0,name.length()-6), outputStream.toByteArray());
                }
            }
        }
        zip.close();
    }

    public List<JavaFileObject> list(String packageName, boolean recurse) {
        List<JavaFileObject> list = new ArrayList<>();
        for (String name : map.keySet()) {
            String path = packageName;
            if (name.startsWith(path)) {
                if (recurse || (name.length() > path.length() && !name.substring(path.length() + 1).contains("."))) {
                    list.add(new JFO(name, map.get(name), Kind.CLASS));
                }
            }
        }
        return list;
    }
}
