package rawfish.artedprvt.api;

import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class APIJavaFileObjects {
    public static Iterable<JavaFileObject> list(String packageName, Set<Kind> kinds, boolean recurse) {
        if(kinds.contains(Kind.CLASS)){
            List<JavaFileObject> classList=classList(packageName,recurse);
            if(classList.isEmpty()){
                return null;
            }
            return classList;
        }
        return null;
    }

    public static List<JavaFileObject> classList(String packageName,boolean recurse){
        List<JavaFileObject> list=new ArrayList<>();
        for(String name:API.map.keySet()){
            String path=packageName.replace('.','/');
            if(name.startsWith(path)&&name.endsWith(".class")){
                if(recurse||(name.length()>path.length()&&!name.substring(path.length()+1).contains("/"))) {
                    list.add(new JFO(name.substring(0,name.length()-6).replace('/','.'), API.map.get(name), Kind.CLASS));
                }
            }
        }
        return list;
    }


    public static class JFO extends SimpleJavaFileObject {
        private byte[] bytes;
        private String name;
        protected JFO(String name,byte[] bytes, Kind kind) {
            super(URI.create(name), kind);
            this.bytes=bytes;
            this.name=name;
        }

        @Override
        public InputStream openInputStream() {
            return new ByteArrayInputStream(bytes);
        }

        public String getBinaryName(){
            return name;
        }


    }
}
