package rawfish.artedprvt.core.struct;

import org.apache.http.util.ByteArrayBuffer;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * apkg文件加载器
 */
public class ApkgFileLoader implements FileLoader {
    public String apkg;
    public Map<String,byte[]> pkgs;
    public ApkgFileLoader(String apkg) throws Exception{
        this.apkg=apkg;
        pkgs=new HashMap<>();
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
                pkgs.put(dediff(entry.getName()),outputStream.toByteArray());
            }
        }
        zip.close();
    }
    @Override
    public String getString(String appendable) {
        byte[] bs=pkgs.get(appendable);
        if(bs==null){
            return null;
        }
        return new String(bs,StandardCharsets.UTF_8);
    }

    @Override
    public InputStream getInputStream(String appendable) {
        byte[] bs=pkgs.get(appendable);
        if(bs==null){
            return null;
        }
        return new ByteArrayInputStream(bs);
    }

    public String dediff(String s){
        return s.replace("\\","/");
    }
}
