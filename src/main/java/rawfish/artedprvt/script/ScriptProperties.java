package rawfish.artedprvt.script;

import java.util.HashMap;
import java.util.Map;

public class ScriptProperties {
    public static Map<String,String> props(){
        return new HashMap<>(props);
    }

    /**
     * 基本属性 对于一个Minecraft实例来说不会变化
     */
    private static Map<String,String> props=new HashMap<String,String>(){{
        put("frame.modid","artedprvt");
        put("frame.name","Artedprvt Frame");
        put("frame.version","1.1@obsidian");
        put("frame.author","Rawfishc");
        put("frame.dir",System.getProperties().get("user.dir").toString()+"/artedprvt");
        put("frame.mcversion","1.8.9");

        put("engine.list","Rhino(js)");
        put("engine.version","Rhino(1.7.15)");
    }};
}
