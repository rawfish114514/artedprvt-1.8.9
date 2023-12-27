package rawfish.artedprvt.work;

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
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ClassByteTool {
    static Map<String,byte[]> compile(String name, String source) throws IOException {
        JavaCompiler compiler=ToolProvider.getSystemJavaCompiler();

        JavaFileObject javaFileObject=new MemoryJavaFileObject(name,source);
        DiagnosticCollector<JavaFileObject> diagnostics=new DiagnosticCollector<>();
        MemoryJavaFileManager fileManager=new MemoryJavaFileManager(compiler.getStandardFileManager(diagnostics, Locale.CHINA,StandardCharsets.UTF_8));
        JavaCompiler.CompilationTask task=compiler.getTask(null,fileManager,diagnostics,null,null,Arrays.asList(javaFileObject));
        boolean success=task.call();

        // 处理编译错误
        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
            System.out.format("Error on line %d in %s%n", diagnostic.getLineNumber(), diagnostic.getSource().toUri());
            System.out.println(diagnostic.getMessage(Locale.CHINA));
        }

        fileManager.close();

        if (!success) {
            throw new RuntimeException("Compilation failed");
        }
        return fileManager.getClassByteMap();
    }

    static ClassLoader inMemoryCreate(Map<String,byte[]> classByteMap){
        return new MemoryClassLoader(classByteMap);
    }


    private static class MemoryClassLoader extends ClassLoader {
        Map<String,byte[]> classByteMap;
        MemoryClassLoader(Map<String,byte[]> classByteMap){
            super(MemoryClassLoader.class.getClassLoader());
            this.classByteMap=new HashMap<>(classByteMap);
        }

        public Class<?> loadClass(String name) throws ClassNotFoundException {
            byte[] classBytes=classByteMap.get(name);
            if (classBytes==null) {
                return super.loadClass(name);
            }
            classByteMap.remove(name);
            return defineClass(name,classBytes,0,classBytes.length);
        }
    }

    private static class MemoryJavaFileManager extends ForwardingJavaFileManager<JavaFileManager>{
        protected MemoryJavaFileManager(JavaFileManager fileManager) {
            super(fileManager);
            classByteMap=new HashMap<>();
        }

        Map<String,byte[]> classByteMap;

        void putClassByte(String name,byte[] bytes){
            classByteMap.put(name,bytes);
        }

        public Map<String,byte[]> getClassByteMap(){
            return new HashMap<>(classByteMap);
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling) throws IOException {
            return new MemoryJavaFileObjectOutput(className);
        }

        class MemoryJavaFileObjectOutput extends SimpleJavaFileObject{
            protected MemoryJavaFileObjectOutput(String name) {
                super(URI.create(name), Kind.CLASS);
                this.name=name;
            }

            String name;

            @Override
            public OutputStream openOutputStream() throws IOException {
                return new ClassOutputStream();
            }

            class ClassOutputStream extends FilterOutputStream{
                public ClassOutputStream() {
                    super(new ByteArrayOutputStream());
                }

                @Override
                public void close() throws IOException {
                    out.close();
                    putClassByte(name,((ByteArrayOutputStream)out).toByteArray());
                }
            }
        }
    }

    private static class MemoryJavaFileObject extends SimpleJavaFileObject{
        protected MemoryJavaFileObject(String name,String source) {
            super(URI.create(name), Kind.SOURCE);
            this.source=source;
        }

        String source;
        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return source;
        }
    }
}
