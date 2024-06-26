package com.artedprvt.work;

import com.artedprvt.api.APIJavaFileObjects;
import com.artedprvt.api.APIJavaFileObjects.JFO;
import com.artedprvt.api.JarClasses;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ClassByteTool {
    public static Map<String, byte[]> compile(Map<String, String> sourceMap,InputStream... jarClassesList) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();


        List<JavaFileObject> sourceList = new ArrayList<>();
        for (String name : sourceMap.keySet()) {
            sourceList.add(new MemoryJavaFileObject(name, sourceMap.get(name)));
        }
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        MemoryJavaFileManager fileManager = new MemoryJavaFileManager(compiler.getStandardFileManager(diagnostics, Locale.CHINA, StandardCharsets.UTF_8));
        for(InputStream jarClasses:jarClassesList){
            fileManager.addJarClasses(jarClasses);
        }
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, sourceList);
        boolean success = task.call();

        StringBuilder sb = new StringBuilder();
        MessageFormat format = new MessageFormat("Error on line {0} in {1}\n");
        // 处理编译错误
        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
            //sb.append(format.format(new Object[]{diagnostic.getLineNumber(), diagnostic.getSource().toUri()}));
            sb.append(diagnostic.getMessage(Locale.CHINA));
        }

        fileManager.close();

        if (!success) {
            throw new RuntimeException("Compilation failed\n\n" + sb);
        }
        return fileManager.getClassByteMap();
    }

    public static ClassLoader inMemoryCreate(Map<String, byte[]> classByteMap) {
        return new MemoryClassLoader(classByteMap);
    }


    private static class MemoryClassLoader extends ClassLoader {
        Map<String, byte[]> classByteMap;

        MemoryClassLoader(Map<String, byte[]> classByteMap) {
            super(ClassByteTool.class.getClassLoader());
            this.classByteMap = new HashMap<>(classByteMap);
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            byte[] classBytes = classByteMap.get(name);
            if (classBytes == null) {
                return super.loadClass(name);
            }
            classByteMap.remove(name);
            return defineClass(name, classBytes, 0, classBytes.length);
        }
    }

    private static class MemoryJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {
        protected MemoryJavaFileManager(JavaFileManager fileManager) {
            super(fileManager);
            classByteMap = new HashMap<>();

            jarClassesList=new ArrayList<>();
        }

        Map<String, byte[]> classByteMap;

        List<JarClasses> jarClassesList;

        void addJarClasses(InputStream inputStream){
            try {
                jarClassesList.add(new JarClasses(inputStream));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        void putClassByte(String name, byte[] bytes) {
            classByteMap.put(name, bytes);
        }


        public Map<String, byte[]> getClassByteMap() {
            return new HashMap<>(classByteMap);
        }

        @Override
        public JavaFileObject getJavaFileForInput(Location location, String className, Kind kind) throws IOException {
            return super.getJavaFileForInput(location, className, kind);
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling) throws IOException {
            return new MemoryJavaFileObjectOutput(className);
        }

        @Override
        public Iterable<JavaFileObject> list(Location location, String packageName, Set<Kind> kinds, boolean recurse) throws IOException {
            List<JavaFileObject> classes=new ArrayList<>();
            List<JavaFileObject> getClasses= APIJavaFileObjects.nativeClasses(packageName,kinds,recurse);
            if(getClasses!=null){
                classes.addAll(getClasses);
            }
            for(JarClasses jarClasses:jarClassesList){
                getClasses=jarClasses.list(packageName,recurse);
                if (getClasses != null) {
                    classes.addAll(getClasses);
                }
            }
            if(!classes.isEmpty()){
                return classes;
            }
            return super.list(location,packageName,kinds,recurse);
        }

        @Override
        public String inferBinaryName(Location location, JavaFileObject file) {
            if(file instanceof APIJavaFileObjects.JFO){
                return ((JFO) file).getBinaryName();
            }
            return super.inferBinaryName(location, file);
        }

        class MemoryJavaFileObjectOutput extends SimpleJavaFileObject {
            protected MemoryJavaFileObjectOutput(String name) {
                super(URI.create(name), Kind.CLASS);
                this.name = name;
            }

            String name;

            @Override
            public OutputStream openOutputStream() throws IOException {
                return new ClassOutputStream();
            }

            class ClassOutputStream extends FilterOutputStream {
                public ClassOutputStream() {
                    super(new ByteArrayOutputStream());
                }

                @Override
                public void close() throws IOException {
                    out.close();
                    putClassByte(name, ((ByteArrayOutputStream) out).toByteArray());
                }
            }
        }
    }

    private static class MemoryJavaFileObject extends SimpleJavaFileObject {
        protected MemoryJavaFileObject(String name, String source) {
            super(URI.create(name), Kind.SOURCE);
            this.source = source;
        }

        String source;

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return source;
        }
    }
}
