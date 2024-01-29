package com.artedprvt.core.app.script.rhino;

import com.artedprvt.core.localization.types.SES;
import org.mozilla.javascript.Scriptable;
import com.artedprvt.core.app.script.ScriptExceptions;
import com.artedprvt.core.app.script.ScriptSystem;

public class SystemMethodArgs extends SystemMethod {
    public SystemMethodArgs(Scriptable scope, ScriptSystem scriptSystem) {
        super(scope, scriptSystem);
        name = "args";
    }

    @Override
    public Object invoke(Object[] args) {
        if (args.length > 0) {
            ScriptExceptions.exception(SES.ses0, this.getName());
        }
        return scriptSystem.getArgs();
    }
}
