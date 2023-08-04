package rawfish.artedprvt.core.struct;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 源文件加载器
 */
public class SourceFileLoader implements FileLoader {
    public String dir;
    public SourceFileLoader(String dir){
        this.dir=dir;
    }
    @Override
    public String getString(String appendable) {
        File file=new File(dir+"/"+appendable);
        if(!file.isFile()){
            return null;
        }
        Reader reader;
        StringBuilder sb;
        try {
            reader=new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            sb=new StringBuilder();
            while(true){
                int n=reader.read();
                if(n==-1) {
                    break;
                }
                sb.append((char)n);
            }
            reader.close();

        } catch (IOException e) {
            return null;
        }
        return sb.toString();
    }

    @Override
    public InputStream getInputStream(String appendable) {
        File file=new File(dir+"/"+appendable);
        if(!file.isFile()){
            return null;
        }
        try {
            return new FileInputStream(file);
        }catch (IOException e){
            return null;
        }
    }
}
