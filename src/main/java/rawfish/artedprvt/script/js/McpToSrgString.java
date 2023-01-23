package rawfish.artedprvt.script.js;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class McpToSrgString {
    public static String str=null;
    public static synchronized String getMcpToSrgString(){
        if(str==null) {
            StringBuilder sb = new StringBuilder();
            try {
                InputStream input=getInputStream();
                //InputStream input = new FileInputStream(new File("C:\\Users\\Administrator\\.gradle\\caches\\minecraft\\de\\oceanlabs\\mcp\\mcp_stable\\22\\srgs/mcp-srg.srg"));
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

    public static InputStream getInputStream(){
        return McpToSrgString.class.getClassLoader().getResourceAsStream("mcp_stable/22/srgs/mcp-srg.srg");
    }
}
