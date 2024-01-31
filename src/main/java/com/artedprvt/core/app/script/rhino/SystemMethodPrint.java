package com.artedprvt.core.app.script.rhino;

import com.artedprvt.core.localization.types.SES;
import org.mozilla.javascript.Scriptable;
import com.artedprvt.core.app.script.ScriptExceptions;
import com.artedprvt.core.app.script.ScriptSystem;

public class SystemMethodPrint extends SystemMethod {
    public SystemMethodPrint(Scriptable scope, ScriptSystem scriptSystem) {
        super(scope, scriptSystem);
        name = "print";
    }

    @Override
    public Object invoke(Object[] args) {
        if (args.length == 0) {
            scriptSystem.print("");
            return null;
        }
        if (args.length == 1) {
            scriptSystem.print(String.valueOf(args[0]));
            return null;
        }
        ScriptExceptions.exception(SES.ses0, this.getName());
        return null;
    }
}