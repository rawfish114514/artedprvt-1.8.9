package rawfish.artedprvt.core.rhino;

import rawfish.artedprvt.Artedprvt;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class McpToSrgString {
    public static String str=null;
    public static synchronized String getMcpToSrgString(){
        if(str==null) {
            StringBuilder sb = new StringBuilder();
            try {
                InputStream input=getInputStream();
                if(input==null){
                    str="";
                    return str;
                }
                Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
                while (true) {
                    int c = reader.read();
                    if (c == -1) {
                        break;
                    }
                    sb.append((char) c);
                }
                reader.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            str=sb.toString();
        }
        return str;
    }

    public static String getResourceName(){
        return "/mapping/mapping_1.8.9.txt";
    }
    public static InputStream getInputStream(){
        return Artedprvt.class.getResourceAsStream(getResourceName());
    }
}
