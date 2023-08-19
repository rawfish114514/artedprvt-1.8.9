package rawfish.artedprvt.core;

import com.electronwill.toml.Toml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrameConfig {
    public static Map<String, Object> config;

    public static List<String> keyList=new ArrayList<String>(){{
        add("language");
        add("repository");
    }};
    public static Map<String,List<String>> valuesMap=new HashMap<String,List<String>>(){{
        put("language",s("zh_cn"));
        put("repository",s("gitee"));
    }};

    public static List<String> s(String... strs){
        List<String> list=new ArrayList<>();
        for(String s:strs){
            list.add(s);
        }
        return list;
    }
    /**
     * 返回有效值
     * @param key
     * @return
     */
    public static List<String> getValues(String key){
        return valuesMap.get(key);
    }

    public static void setValue(String key,Object value){
        config.put(key,value);
    }

    public static Object getValue(String key){
        return config.get(key);
    }
    public static synchronized void load(){
        try {
            if(new File(FrameProperties.props().get("frame.dir")+"/.artedprvt/config.toml").isFile()){
                //从文件加载配置
                loadFromFile();
            }else{
                if (new File(FrameProperties.props().get("frame.dir")+"/.artedprvt").isDirectory()) {
                    //加载默认配置并写入文件
                    loadDefault();
                    writeToFile();
                } else {
                    //加载默认配置
                    loadDefault();
                }
            }
        }catch (Exception e){
            e.printStackTrace(System.out);
        }
        config.forEach((k,v)->{if(!keyList.contains(k))config.remove(k);});
        Localization.load();
    }

    public static void updateFile() throws Exception{
        File file=new File(FrameProperties.props().get("frame.dir")+"/.artedprvt/config.toml");
        if(file.isFile()){
            String string=Toml.writeToString(config);
            Writer writer=new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            writer.write(string);
            writer.flush();
            writer.close();
        }
    }

    public static void loadDefault(){
        config=new HashMap<>();
        config.put("language","zh_cn");
        config.put("repository","gitee");
    }

    public static void loadFromFile() throws Exception{
        config=new HashMap<>();
        File file=new File(FrameProperties.props().get("frame.dir")+"/.artedprvt/config.toml");
        Reader reader=new InputStreamReader(new FileInputStream(file));
        StringBuilder sb=new StringBuilder();
        int n;
        while((n=reader.read())!=-1){
            sb.append((char)n);
        }
        config.putAll(Toml.read(sb.toString()));
    }

    public static void writeToFile() throws Exception{
        File file=new File(FrameProperties.props().get("frame.dir")+"/.artedprvt/config.toml");
        if(!file.isFile()){
            file.createNewFile();
        }
        String string=Toml.writeToString(config);
        Writer writer=new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        writer.write(string);
        writer.flush();
        writer.close();
    }
}
