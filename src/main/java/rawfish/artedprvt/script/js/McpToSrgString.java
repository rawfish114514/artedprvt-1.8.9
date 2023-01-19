package rawfish.artedprvt.script.js;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class McpToSrgString {
    public static String str=null;
    public static String getMcpToSrgString(){
        if(str==null) {
            StringBuilder sb = new StringBuilder();
            try {
                File file = new File("C:\\Users\\Administrator\\.gradle\\caches\\minecraft\\de\\oceanlabs\\mcp\\mcp_stable\\22\\srgs/mcp-srg.srg");
                Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
                while (true) {
                    int c = reader.read();
                    if (c == -1) {
                        break;
                    }
                    sb.append((char) c);
                }
                reader.close(); // 关闭流
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            str=sb.toString();
        }
        return str;
    }
}
