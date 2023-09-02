package rawfish.artedprvt.core.engine;

import java.util.ArrayList;
import java.util.List;

public class ScriptEngineFactorys {
    private static List<ScriptEngineFactory<? extends ScriptEngine>> factoryList=new ArrayList<>();

    public static void reg(ScriptEngineFactory<? extends ScriptEngine> scriptEngineFactory){
        factoryList.add(scriptEngineFactory);
    }

    public static List<ScriptEngineFactory<? extends ScriptEngine>> factories(){
        return new ArrayList<>(factoryList);
    }
}
