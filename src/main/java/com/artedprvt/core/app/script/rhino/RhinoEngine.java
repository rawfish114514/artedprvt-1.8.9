package com.artedprvt.core.app.script.rhino;

import com.artedprvt.core.app.script.ScriptProcess;
import com.artedprvt.core.app.script.engine.ScriptEngine;
import com.artedprvt.core.app.script.struct.ScriptModule;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import com.artedprvt.core.app.script.ScriptSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RhinoEngine implements ScriptEngine {
    private ScriptProcess process;
    private Context rhino;
    private boolean hasInit;

    public RhinoEngine(ScriptProcess process) {
        this.process = process;
        hasInit = false;
    }

    public void init() {
        if (!hasInit) {
            hasInit = true;
            rhino = Context.enter();
            rhino.unseal();
            rhino.setOptimizationLevel(-1);
            rhino.setLocale(Locale.ENGLISH);
        }
    }

    @Override
    public boolean isExecutable(ScriptModule scriptModule) {
        return Rhino.LANGUAGE.equals(scriptModule.getScriptLanguage());
    }

    @Override
    public void execute(ScriptModule scriptModule) {
        ScriptSystem scriptSystem = process.getScriptSystem();
        ScriptableObject scope = rhino.initStandardObjects();
        scope.put("_name", scope, scriptModule.getModuleFullNameLiteral());
        List<SystemMethod> systemMethodList = buildSystemMethodList(scriptSystem, scriptModule, rhino, scope);
        for (SystemMethod systemMethod : systemMethodList) {
            scope.put(systemMethod.getName(), scope, systemMethod);
        }
        rhino.evaluateString(scope, scriptModule.getSource(), scriptModule.getModuleFullNameLiteral(), 1, null);
    }

    public static List<SystemMethod> buildSystemMethodList(ScriptSystem scriptSystem, ScriptModule scriptModule, Context context, Scriptable scope) {
        List<SystemMethod> list = new ArrayList<>();
        list.add(new SystemMethodArgs(scope, scriptSystem));
        list.add(new SystemMethodAssets(scope, scriptSystem));
        list.add(new SystemMethodAssetsis(scope, scriptSystem));
        list.add(new SystemMethodExit(scope, scriptSystem));
        list.add(new SystemMethodExport(scope, scriptSystem, scriptModule));
        list.add(new SystemMethodImport(scope, scriptSystem));
        list.add(new SystemMethodInfo(scope, scriptSystem));
        list.add(new SystemMethodPrint(scope, scriptSystem));
        list.add(new SystemMethodSleep(scope, scriptSystem));
        list.add(new SystemMethodThread(scope, scriptSystem));
        return list;
    }
}
