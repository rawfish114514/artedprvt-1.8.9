package rawfish.artedprvt.core.app.script.rhino;

import org.mozilla.javascript.Scriptable;
import rawfish.artedprvt.core.app.script.ScriptExceptions;
import rawfish.artedprvt.core.app.script.ScriptSystem;
import rawfish.artedprvt.core.localization.types.SES;

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
