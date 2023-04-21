package rawfish.artedprvt.script;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * apkg文件加载器
 */
public class ApkgFileLoader implements FileLoader{
    public String apkg;
    public Map<String,String> pkgs;
    public ApkgFileLoader(String apkg) throws Exception{
        this.apkg=apkg;
        pkgs=new HashMap<>();
        ZipInputStream zip = new ZipInputStream(
                new FileInputStream(apkg),
                Charset.forName("GBK"));

        Reader reader=new InputStreamReader(zip, StandardCharsets.UTF_8);
        ZipEntry entry = null;
        while ((entry = zip.getNextEntry()) != null) {
            String name = entry.getName();
            if (!entry.isDirectory()) {
                int n;
                int i=0;
                StringBuilder sb=new StringBuilder(0);
                while ((n = reader.read()) != -1) {
                    sb.append((char)n);
                }
                pkgs.put(entry.getName(),sb.toString());
            }
        }
    }
    @Override
    public String readFile(String appendable) {
        return pkgs.get(appendable);
    }
}
