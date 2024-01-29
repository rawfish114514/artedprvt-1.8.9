package com.artedprvt.core.app.script.rhino;

import com.artedprvt.core.localization.types.SES;
import org.mozilla.javascript.Scriptable;
import com.artedprvt.core.app.script.ScriptExceptions;
import com.artedprvt.core.app.script.ScriptSystem;

public class SystemMethodSleep extends SystemMethod {
    public SystemMethodSleep(Scriptable scope, ScriptSystem scriptSystem) {
        super(scope, scriptSystem);
        name = "sleep";
    }

    @Override
    public Object invoke(Object[] args) {
        if (args.length == 1) {
            if (args[0] instanceof Number) {
                scriptSystem.sleep(((Number) args[0]).longValue());
                return null;
            } else {
                ScriptExceptions.exception(SES.ses0, this.getName());
            }
        }
        ScriptExceptions.exception(SES.ses0, this.getName());
        return null;
    }
}
