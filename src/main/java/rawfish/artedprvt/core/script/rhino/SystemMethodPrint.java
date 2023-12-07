package rawfish.artedprvt.core.script.rhino;

import org.mozilla.javascript.Scriptable;
import rawfish.artedprvt.core.localization.types.SES;
import rawfish.artedprvt.core.script.ScriptExceptions;
import rawfish.artedprvt.core.script.ScriptSystem;

public class SystemMethodPrint extends SystemMethod{
    public SystemMethodPrint(Scriptable scope, ScriptSystem scriptSystem) {
        super(scope,scriptSystem);
        name="print";
    }

    @Override
    public Object invoke(Object[] args) {
        if(args.length==0){
            scriptSystem.print(ScriptSystem.CHAT,"");
            return null;
        }
        if(args.length==1){
            scriptSystem.print(ScriptSystem.CHAT,String.valueOf(args[0]));
            return null;
        }
        ScriptExceptions.exception(SES.ses0,this.getName());
        return null;
    }
}
