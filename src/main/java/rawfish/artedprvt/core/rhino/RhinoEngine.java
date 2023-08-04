package rawfish.artedprvt.core.rhino;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;
import rawfish.artedprvt.core.ScriptLanguage;
import rawfish.artedprvt.core.ScriptProcess;
import rawfish.artedprvt.core.ScriptSystem;
import rawfish.artedprvt.core.engine.ScriptEngine;
import rawfish.artedprvt.core.struct.ScriptModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RhinoEngine implements ScriptEngine {
    private ScriptProcess process;
    private Context rhino;
    private boolean hasInit;
    public RhinoEngine(ScriptProcess process){
        this.process=process;
        hasInit=false;
    }

    public void init(){
        if(!hasInit){
            hasInit=true;
            rhino=Context.enter();
            rhino.unseal();
            rhino.setOptimizationLevel(-1);
            rhino.setLocale(Locale.ENGLISH);
        }
    }
    @Override
    public boolean isExecutable(ScriptModule scriptModule) {
        return scriptModule.getScriptLanguage()== ScriptLanguage.JAVASCRIPT;
    }

    @Override
    public void execute(ScriptModule scriptModule) {
        ScriptSystem scriptSystem=process.getScriptSystem();
        ScriptableObject scope=rhino.initStandardObjects();
        scope.put("_name",scope,scriptModule.getModuleFullName());
        List<SystemMethod> systemMethodList=buildSystemMethodList(scriptSystem,scriptModule,rhino);
        for(SystemMethod systemMethod:systemMethodList){
            scope.put(systemMethod.getName(),scope,systemMethod);
        }
        rhino.evaluateString(scope,scriptModule.getSource(),scriptModule.getModuleFullName(),1,null);
    }

    public static List<SystemMethod> buildSystemMethodList(ScriptSystem scriptSystem,ScriptModule scriptModule,Context context){
        List<SystemMethod> list=new ArrayList<>();
        list.add(new SystemMethodArgs(scriptSystem));
        list.add(new SystemMethodAssets(scriptSystem));
        list.add(new SystemMethodAssetsis(scriptSystem));
        list.add(new SystemMethodExit(scriptSystem));
        list.add(new SystemMethodExport(scriptSystem,scriptModule));
        list.add(new SystemMethodImport(scriptSystem));
        list.add(new SystemMethodInfo(scriptSystem));
        list.add(new SystemMethodPrint(scriptSystem));
        list.add(new SystemMethodProps(scriptSystem));
        list.add(new SystemMethodSleep(scriptSystem));
        list.add(new SystemMethodThread(scriptSystem));
        return list;
    }
}
