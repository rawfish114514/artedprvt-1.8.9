package rawfish.artedprvt.core.rhino;

import org.mozilla.javascript.Scriptable;
import rawfish.artedprvt.core.ScriptExceptions;
import rawfish.artedprvt.core.ScriptSystem;
import rawfish.artedprvt.core.localization.types.SES;

public class SystemMethodInfo extends SystemMethod{
    public SystemMethodInfo(Scriptable scope, ScriptSystem scriptSystem) {
        super(scope,scriptSystem);
        name="info";
    }

    @Override
    public Object invoke(Object[] args) {
        if(args.length>0){
            ScriptExceptions.exception(SES.ses0,this.getName());
        }
        return scriptSystem.getInfo();
    }
}
