package rawfish.artedprvt.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 静态配置
 */
public class ScriptConfig {
    /**
     * 更新配置
     */
    public static ScriptConfig load(){
        ScriptConfig config=new ScriptConfig();
        String path=System.getProperties().get("user.dir").toString()+"/artedprvt/config.json";
        Context rhino=Context.enter();
        ScriptableObject scope=rhino.initStandardObjects();

        File file=new File(path);
        if(!file.exists()){
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
            throw new RuntimeException(e);
        }
        String str=sb.toString();

        scope.put("str",scope,str);
        rhino.evaluateString(scope,getScript(),"114514",114514,null);

        Object object=scope.get("optionsList");
        if(object instanceof List){
            List options=(List)object;
            config.options.addAll(options);
        }

        return config;
    }
    public List<String> options=new ArrayList<>();


    public static String getScript(){
        return "var config=JSON.parse(str);" +
                "var options=config.options;" +
                "var optionsList=new java.util.ArrayList();" +
                "if(options instanceof Array){for(var i=0;i<options.length;i++){optionsList.add(options[i]);}};";
    }
}
