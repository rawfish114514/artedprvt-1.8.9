package rawfish.artedprvt.core.engine;

import rawfish.artedprvt.core.ScriptLanguage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Engines {
    private static List<Engine> engineList=new ArrayList<>();
    public static void reg(Engine engine){
        engineList.add(engine);
    }

    public static List<String> getNames(){
        return engineList.stream().map(Engine::getEngineName).collect(Collectors.toList());
    }

    public static List<ScriptLanguage> getLanguages(){
        return engineList.stream().map(Engine::getScriptLanguage).collect(Collectors.toList());
    }

    public static List<ScriptEngineFactory> getEngineFactorys(){
        return engineList.stream().map(Engine::getScriptEngineFactory).collect(Collectors.toList());
    }

    public static List<ScriptStackParserFactory> getStackParserFactorys(){
        return engineList.stream().map(Engine::getScriptStackParserFactory).collect(Collectors.toList());
    }

    public static List<ServiceEngine> getServiceEngines(){
        return engineList.stream().map(Engine::getServiceEngine).collect(Collectors.toList());
    }

    public static ServiceEngine getService(String abbr){
        Engine engine=getEngineOfAbbr(abbr);
        if(engine!=null){
            return engine.getServiceEngine();
        }
        return null;
    }

    public static ScriptLanguage getLanguageOfAbbr(String abbr){
        Engine engine=getEngineOfAbbr(abbr);
        if(engine!=null){
            return engine.getScriptLanguage();
        }
        return null;
    }

    public static Engine getEngineOfAbbr(String abbr){
        Engine engine;
        for(int i=0;i<engineList.size();i++){
            engine=engineList.get(i);
            if(engine.getScriptLanguage().equals(abbr)){
                return engine;
            }
        }
        return null;
    }
}
