package rawfish.artedprvt.core.script.engine;

public class ScriptEngineInit {
    public static void init(){
        for(ScriptEngineFactory factory:Engines.getEngineFactorys()){
            ScriptEngine scriptEngine=factory.create(null);
            new ScriptEngineInitThread(scriptEngine).start();
        }
    }
}