package com.artedprvt.api;

import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class APIJavaFileObjects {
    private static Map<String, byte[]> map = new HashMap<>();

    private static byte[] getBytes(String name) {
        if (!map.containsKey(name)) {
            try {
                map.put(name, readBytes(name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map.get(name);
    }

    private static byte[] readBytes(String name) throws IOException {
        InputStream inputStream = APIJavaFileObjects.class.getResourceAsStream("/" + name);
        if (inputStream == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int n;
        while ((n = inputStream.read()) != -1) {
            byteArrayOutputStream.write(n);
        }
        return byteArrayOutputStream.toByteArray();
    }


    public static List<JavaFileObject> nativeClasses(String packageName, Set<Kind> kinds, boolean recurse) {
        if (kinds.contains(Kind.CLASS)) {
            List<JavaFileObject> classList = nativeClasses(packageName, recurse);
            if (classList.isEmpty()) {
                return null;
            }
            return classList;
        }
        return null;
    }

    private static List<JavaFileObject> nativeClasses(String packageName, boolean recurse) {
        List<JavaFileObject> list = new ArrayList<>();
        for (String name : CS.strings) {
            String path = packageName;
            if (name.startsWith(path)) {
                if (recurse || (name.length() > path.length() && !name.substring(path.length() + 1).contains("."))) {
                    list.add(new JFO(name, getBytes(name.replace('.','/')+".class"), Kind.CLASS));
                }
            }
        }
        return list;
    }


    public static class JFO extends SimpleJavaFileObject {
        private byte[] bytes;
        private String name;

        protected JFO(String name, byte[] bytes, Kind kind) {
            super(URI.create(name), kind);
            this.bytes = bytes;
            this.name = name;
        }

        @Override
        public InputStream openInputStream() {
            return new ByteArrayInputStream(bytes);
        }

        public String getBinaryName() {
            return name;
        }


    }

    private static class CS{
        public static List<String> strings;

        static {
            try {
                strings = readClasses();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private static List<String> readClasses() throws IOException {
            Reader reader=new InputStreamReader(CS.class.getResourceAsStream("/classes.txt"), StandardCharsets.UTF_8);
            StringBuilder sb=new StringBuilder();
            int n;

            while((n=reader.read())!=-1){
                sb.append((char)n);
            }
            return Arrays.stream(sb.toString().split(";")).map(String::trim).collect(Collectors.toList());
        }
    }
}
