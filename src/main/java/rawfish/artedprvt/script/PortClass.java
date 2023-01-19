package rawfish.artedprvt.script;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaClass;
import org.mozilla.javascript.ScriptableObject;
import rawfish.artedprvt.script.mi.EventListener;
import rawfish.artedprvt.script.mi.Events;
import rawfish.artedprvt.script.mi.WorldManager;

import java.util.HashMap;
import java.util.Map;

public class PortClass {
    protected Map<String,NativeJavaClass> classes;//类简单名:本地java类
    protected ScriptProcess pro;
    protected Context rhino;
    protected ScriptableObject scope;
    public PortClass(ScriptProcess proIn){
        pro=proIn;
        classes=new HashMap<>();

        rhino=pro.rhino;
        scope=rhino.initStandardObjects();

        add();
    }

    private void add(){
        put(Items.class);
        put(Blocks.class);
        put(Events.class);
        put(EventListener.class);
        put(WorldManager.class);
        put(BlockPos.class);
    }
    private void put(Class clas){
        classes.put(clas.getSimpleName(),new NativeJavaClass(scope,clas));
    }
}
