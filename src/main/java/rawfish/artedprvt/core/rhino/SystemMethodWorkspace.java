package rawfish.artedprvt.core.rhino;

import org.mozilla.javascript.Scriptable;
import rawfish.artedprvt.core.ScriptExceptions;
import rawfish.artedprvt.core.ScriptSystem;
import rawfish.artedprvt.core.localization.types.SES;

public class SystemMethodWorkspace extends SystemMethod {
    public SystemMethodWorkspace(Scriptable scope, ScriptSystem scriptSystem) {
        super(scope,scriptSystem);
        name="workspace";
    }

    @Override
    public Object invoke(Object[] args) {
        if(args.length>0){
            ScriptExceptions.exception(SES.ses0,this.getName());
        }
        return scriptSystem.getWorkSpace();
    }
}
