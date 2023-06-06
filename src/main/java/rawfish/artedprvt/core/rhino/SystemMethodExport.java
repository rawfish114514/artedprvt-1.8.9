package rawfish.artedprvt.core.rhino;

import rawfish.artedprvt.core.ScriptExceptions;
import rawfish.artedprvt.core.ScriptSystem;
import rawfish.artedprvt.core.struct.ScriptModule;

public class SystemMethodExport extends SystemMethod{
    private ScriptModule scriptModule;
    public SystemMethodExport(ScriptSystem scriptSystem, ScriptModule scriptModule) {
        super(scriptSystem);
        this.scriptModule=scriptModule;
        name="export";
    }

    @Override
    public Object invoke(Object[] args) {
        if(args.length==1){
            scriptSystem.exportModule(scriptModule,args[0]);
            return null;
        }
        ScriptExceptions.exceptionSystemMethodInvoke(this);
        return null;
    }
}
