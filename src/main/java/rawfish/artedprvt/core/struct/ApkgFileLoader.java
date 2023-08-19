package rawfish.artedprvt.core.struct;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * apkg文件加载器
 */
public class ApkgFileLoader implements FileLoader {
    public String apkg;
    public Map<String,byte[]> map;
    public ApkgFileLoader(String apkg) throws Exception{
        this.apkg=apkg;
        map =new HashMap<>();
        ZipInputStream zip = new ZipInputStream(
                new FileInputStream(apkg),
                Charset.forName("cp437"));

        ZipEntry entry = null;
        while ((entry = zip.getNextEntry()) != null) {
            if (!entry.isDirectory()) {
                int n;
                ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
                while ((n = zip.read()) != -1) {
                    outputStream.write(n);
                }
                map.put(dediff(entry.getName()),outputStream.toByteArray());
            }
        }
        zip.close();
    }
    @Override
    public String getContent(String path) {
        byte[] bs= map.get(path);
        if(bs==null){
            return null;
        }
        return new String(bs,StandardCharsets.UTF_8);
    }

    @Override
    public InputStream getInputStream(String path) {
        byte[] bs= map.get(path);
        if(bs==null){
            return null;
        }
        return new ByteArrayInputStream(bs);
    }

    @Override
    public boolean isExists(String path) {
        return map.containsKey(path);
    }

    @Override
    public List<String> getPaths() {
        return new ArrayList<>(map.keySet());
    }

    public String dediff(String s){
        return s.replace("\\","/");
    }
}
