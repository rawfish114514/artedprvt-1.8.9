package rawfish.artedprvt.core.rhino;

import rawfish.artedprvt.core.ScriptExceptions;
import rawfish.artedprvt.core.ScriptSystem;

public class SystemMethodInfo extends SystemMethod{
    public SystemMethodInfo(ScriptSystem scriptSystem) {
        super(scriptSystem);
        name="info";
    }

    @Override
    public Object invoke(Object[] args) {
        if(args.length>0){
            ScriptExceptions.exceptionSystemMethodInvoke(this);
        }
        return scriptSystem.getInfo();
    }
}
