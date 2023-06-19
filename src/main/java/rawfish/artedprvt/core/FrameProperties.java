package rawfish.artedprvt.core;

import java.util.HashMap;
import java.util.Map;

public class FrameProperties {
    public static Map<String,String> props(){
        return new HashMap<>(props);
    }

    public static Map<String,String> props;
    static {
        upData();
    }

    public static void upData(){
        Map<String,String> props=new HashMap<String,String>();
        props.put("frame.modid","artedprvt");
        props.put("frame.name","Artedprvt Frame");
        props.put("frame.version","1.2@dustdirt");
        props.put("frame.author","Rawfishc");
        props.put("frame.dir",System.getProperties().get("user.dir").toString()+"/artedprvt");
        props.put("frame.mcversion","1.8.9");

        props.put("engine.list","Rhino(js)");

        FrameProperties.props=props;
    }
}
