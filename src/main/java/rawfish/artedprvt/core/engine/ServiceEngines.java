package rawfish.artedprvt.core.engine;

import rawfish.artedprvt.core.ScriptLanguage;

import java.util.ArrayList;
import java.util.List;

public class ServiceEngines {
    private static List<ServiceEngine> serviceEngineList=new ArrayList<>();

    public static void reg(ServiceEngine serviceEngine){
        serviceEngineList.add(serviceEngine);
    }

    public static ServiceEngine getService(String abbr){
        ScriptLanguage language=ScriptLanguage.abbrOf(abbr);
        if(language==null){
            return null;
        }

        ServiceEngine engine;
        for (int i = 0; i < serviceEngineList.size(); i++) {
            engine=serviceEngineList.get(i);
            if(engine.isExecutable(language)){
                return engine;
            }
        }
        return null;
    }
}
