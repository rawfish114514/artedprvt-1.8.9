package com.artedprvt.core.app.script.rhino;

import com.artedprvt.core.localization.types.SES;
import org.mozilla.javascript.Scriptable;
import com.artedprvt.core.app.script.ScriptExceptions;
import com.artedprvt.core.app.script.ScriptSystem;

public class SystemMethodInfo extends SystemMethod {
    public SystemMethodInfo(Scriptable scope, ScriptSystem scriptSystem) {
        super(scope, scriptSystem);
        name = "info";
    }

    @Override
    public Object invoke(Object[] args) {
        if (args.length > 0) {
            ScriptExceptions.exception(SES.ses0, this.getName());
        }
        return scriptSystem.getInfo();
    }
}
