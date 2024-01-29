package com.artedprvt.core.app.script.struct;

import com.artedprvt.core.app.script.ScriptLanguage;

import java.util.ArrayList;
import java.util.List;

/**
 * 包
 */
public class ScriptPackage {
    private String packageName;
    private List<ScriptModule> moduleList;

    public ScriptPackage(String packageName) {
        this.packageName = packageName;
        moduleList = new ArrayList<>();
    }

    public void add(ScriptModule scriptModule) {
        moduleList.add(scriptModule);
        if (scriptModule.getScriptPackage() != this) {
            //妈妈生的
        }
    }

    public synchronized ScriptModule get(String moduleName) {
        for (ScriptModule scriptModule : moduleList) {
            if (scriptModule.getModuleName().equals(moduleName)) {
                return scriptModule;
            }
        }
        return null;
    }

    public synchronized ScriptModule get(String moduleName, ScriptLanguage scriptLanguage) {
        for (ScriptModule scriptModule : moduleList) {
            if (scriptModule.getModuleName().equals(moduleName) && scriptModule.getScriptLanguage() == scriptLanguage) {
                return scriptModule;
            }
        }
        return null;
    }

    public String getPackageName() {
        return packageName;
    }

    @Override
    public String toString() {
        return "ScriptPackage(name=" + (packageName.equals("") ? "default" : packageName) + ")";
    }
}
