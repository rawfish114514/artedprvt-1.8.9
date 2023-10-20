package rawfish.artedprvt.core.rhino;

import org.mozilla.javascript.Scriptable;
import rawfish.artedprvt.core.ScriptExceptions;
import rawfish.artedprvt.core.ScriptSystem;
import rawfish.artedprvt.core.localization.types.SES;
import rawfish.artedprvt.core.struct.ScriptModule;

public class SystemMethodExport extends SystemMethod{
    private ScriptModule scriptModule;
    public SystemMethodExport(Scriptable scope, ScriptSystem scriptSystem, ScriptModule scriptModule) {
        super(scope,scriptSystem);
        this.scriptModule=scriptModule;
        name="export";
    }

    @Override
    public Object invoke(Object[] args) {
        if(args.length==1){
            scriptSystem.exportModule(scriptModule,args[0]);
            return null;
        }
        ScriptExceptions.exception(SES.ses0,this.getName());
        return null;
    }
}
