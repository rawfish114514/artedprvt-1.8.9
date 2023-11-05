package rawfish.artedprvt.core.script.rhino;

import org.mozilla.javascript.Scriptable;
import rawfish.artedprvt.core.script.ScriptExceptions;
import rawfish.artedprvt.core.script.ScriptSystem;
import rawfish.artedprvt.core.localization.types.SES;

public class SystemMethodExit extends SystemMethod{
    public SystemMethodExit(Scriptable scope, ScriptSystem scriptSystem) {
        super(scope,scriptSystem);
        name="exit";
    }

    @Override
    public Object invoke(Object[] args) {
        if(args.length==1){
            if(args[0] instanceof Number) {
                scriptSystem.exit(((Number) args[0]).intValue());
                return null;
            }else{
                ScriptExceptions.exception(SES.ses0,this.getName());
            }
        }
        ScriptExceptions.exception(SES.ses0,this.getName());
        return null;
    }
}