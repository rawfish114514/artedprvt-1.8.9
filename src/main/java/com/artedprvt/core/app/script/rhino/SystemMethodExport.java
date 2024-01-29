package com.artedprvt.core.app.script.rhino;

import com.artedprvt.core.app.script.struct.ScriptModule;
import com.artedprvt.core.localization.types.SES;
import org.mozilla.javascript.Scriptable;
import com.artedprvt.core.app.script.ScriptExceptions;
import com.artedprvt.core.app.script.ScriptSystem;

public class SystemMethodExport extends SystemMethod {
    private ScriptModule scriptModule;

    public SystemMethodExport(Scriptable scope, ScriptSystem scriptSystem, ScriptModule scriptModule) {
        super(scope, scriptSystem);
        this.scriptModule = scriptModule;
        name = "export";
    }

    @Override
    public Object invoke(Object[] args) {
        if (args.length == 1) {
            scriptSystem.exportModule(scriptModule, args[0]);
            return null;
        }
        ScriptExceptions.exception(SES.ses0, this.getName());
        return null;
    }
}
