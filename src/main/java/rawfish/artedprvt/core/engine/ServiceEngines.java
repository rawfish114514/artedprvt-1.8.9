package rawfish.artedprvt.core.engine;

import org.checkerframework.framework.qual.DefaultQualifier;
import rawfish.artedprvt.core.ScriptLanguage;
import rawfish.artedprvt.core.rhino.ServiceRhinoEngine;

import java.util.ArrayList;
import java.util.List;

public class ServiceEngines {
    private static List<ServiceEngine> serviceEngineList;
    public static synchronized void init(){
        if(serviceEngineList!=null){
            return;
        }
        serviceEngineList=new ArrayList<>();

        serviceEngineList.add(new ServiceRhinoEngine());
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
