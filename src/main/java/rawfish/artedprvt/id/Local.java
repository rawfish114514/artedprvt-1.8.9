package rawfish.artedprvt.id;

import org.tomlj.Toml;
import rawfish.artedprvt.Artedprvt;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class Local {
    private static String language="zh_cn";
    private static String lastLanguage="";

    public static String getLanguage(){
        return language;
    }

    public static boolean isChange(){
        return !language.equals(lastLanguage);
    }

    public static synchronized void load(){
        if(isChange()){
            translation=new HashMap<>();
            try {
                String path = "/language/" + language + "/list.txt";
                InputStream input = Artedprvt.class.getResourceAsStream(path);
                StringBuilder sb = new StringBuilder();
                int n = 0;
                while ((n = input.read()) != -1) {
                    sb.append((char)n);
                }
                String list=sb.toString();
                String[] array=list.split("\n");
                for(String v:array){
                    path="/language/" + language + "/"+v;
                    path=path.replace("\r","");
                    InputStream input0= Artedprvt.class.getResourceAsStream(path);
                    Reader reader=new InputStreamReader(input0, StandardCharsets.UTF_8);
                    sb=new StringBuilder();
                    while ((n = reader.read()) !=-1) {
                        sb.append((char)n);
                    }
                    String str=sb.toString();
                    Map<String,String> table=parse(str);
                    translation.putAll(table);
                }
            }catch (Exception e){
                e.printStackTrace(System.err);
            }
        }
        lastLanguage=language;
    }

    private static Map<String,String> parse(String str){
        Map<String,Object> result= Toml.parse(str).toMap();
        Map<String,String> result0 = new HashMap<>();
        for (Map.Entry<String, Object> entry : result.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String stringValue = String.valueOf(value);
            result0.put(key, stringValue);
        }
        return result0;
    }

    private static Map<String,String> translation;

    public static String getTranslate(String key,Object... args){
        String value=translation.get(key);
        if(value==null){
            return "";
        }
        return MessageFormat.format(value,args);
    }
}
