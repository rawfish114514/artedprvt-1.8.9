package rawfish.artedprvt.core.engine;

import java.util.List;

public class ScriptEngineInit {
    public static void init(){
        List<ScriptEngineFactory<? extends ScriptEngine>> scriptEngineFactories=ScriptEngineFactorys.factories();
        for(ScriptEngineFactory<? extends ScriptEngine> factory:scriptEngineFactories){
            ScriptEngine scriptEngine=factory.create(null);
            new ScriptEngineInitThread(scriptEngine).start();
        }
    }
}
