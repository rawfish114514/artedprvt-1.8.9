package rawfish.artedprvt.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 静态配置
 */
public class ScriptConfig {
    /**
     * 更新配置
     */
    public static ScriptConfig load(String dir){
        String path=dir+"/config.json";

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

        return loads(str);
    }

    public static ScriptConfig loads(String str){
        if(str==null){
            return null;
        }
        ScriptConfig config=new ScriptConfig();
        Context rhino=Context.enter();
        ScriptableObject scope=rhino.initStandardObjects();
        scope.put("str",scope,str);
        rhino.evaluateString(scope,getScript(),"114514",114514,null);

        Object object=scope.get("config");

        if(object instanceof Map){
            Map map=(Map)object;

            //List:options
            Object options=map.get("options");
            if(options!=null) {
                if (options instanceof List) {
                    config.options=(List)options;
                }else{
                    config.error();
                }
            }

            //Map:pkg
            Object pkg=map.get("pkg");
            if(pkg instanceof Map){
                Map mapPkg=(Map)pkg;
                //String:pack
                Object pack=mapPkg.get("pack");
                if(!(pack instanceof String)){
                    config.pkgError();
                }

                config.pkg=mapPkg;
            }else{
                config.pkgError();
            }
        }else{
            config.error();
        }

        return config;
    }

    public List options=null;
    public Map pkg=null;
    public boolean error=false;
    public boolean pkgError=false;

    public void error(){
        error=true;
    }
    public void pkgError(){
        pkgError=true;
    }

    public static String getScript(){
        return "var config=JSON.parse(str);";
    }
}
