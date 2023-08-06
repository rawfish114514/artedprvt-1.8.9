package rawfish.artedprvt.mi.group;

import rawfish.artedprvt.core.ClassGroup;
import rawfish.artedprvt.mi.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有的类
 */
public class ClassGroupMi implements ClassGroup {
    private List<Class> classes;
    public List<Class> getClasses(){
        init();
        add(ChatProvider.class);
        add(EffectSpawn.class);
        add(EventFunction.class);
        add(EventListener.class);
        add(Events.class);
        add(GameClient.class);
        add(GameServer.class);
        add(MapGraphics.class);
        add(MapOperator.class);
        add(PrintChat.class);
        add(TagBuilder.class);
        add(TagBuilder.ListTagBuilder.class);
        add(TagBuilder.CompoundTagBuilder.class);
        return classes();
    }
    
    private void init(){
        classes=new ArrayList<Class>();
    }
    
    private void add(Class clas){
        classes.add(clas);
    }

    private List<Class> classes(){
        return classes;
    }

    public String getName(){
        return "mi";
    }
}
