package com.artedprvt.core.app.script.rhino;

import com.artedprvt.core.localization.types.SES;
import org.mozilla.javascript.Scriptable;
import com.artedprvt.core.app.script.ScriptExceptions;
import com.artedprvt.core.app.script.ScriptSystem;

public class SystemMethodAssets extends SystemMethod {
    public SystemMethodAssets(Scriptable scope, ScriptSystem scriptSystem) {
        super(scope, scriptSystem);
        name = "assets";
    }

    @Override
    public Object invoke(Object[] args) {
        if (args.length > 1) {
            ScriptExceptions.exception(SES.ses0, this.getName());
        }
        return scriptSystem.getFile("assets/" + String.valueOf(args[0]));
    }
}
