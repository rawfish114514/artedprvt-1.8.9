package rawfish.artedprvt.core.app.script.rhino;

import org.mozilla.javascript.Scriptable;
import rawfish.artedprvt.core.app.script.ScriptExceptions;
import rawfish.artedprvt.core.app.script.ScriptSystem;
import rawfish.artedprvt.core.localization.types.SES;

public class SystemMethodAssetsis extends SystemMethod {
    public SystemMethodAssetsis(Scriptable scope, ScriptSystem scriptSystem) {
        super(scope, scriptSystem);
        name = "assetsis";
    }

    @Override
    public Object invoke(Object[] args) {
        if (args.length > 1) {
            ScriptExceptions.exception(SES.ses0, this.getName());
        }
        return scriptSystem.getFileInputStream("assets/" + String.valueOf(args[0]));
    }
}
