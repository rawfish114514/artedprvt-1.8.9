package rawfish.artedprvt.script;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 源文件文件加载器
 */
public class SourceFileLoader implements FileLoader{
    public String dir;
    public SourceFileLoader(String dir){
        this.dir=dir;
    }
    @Override
    public String readFile(String appendable) {
        File file=new File(dir+"/"+appendable);
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
}
