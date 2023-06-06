package rawfish.artedprvt.core.rhino;

import rawfish.artedprvt.core.ScriptExceptions;
import rawfish.artedprvt.core.ScriptSystem;

public class SystemMethodExit extends SystemMethod{
    public SystemMethodExit(ScriptSystem scriptSystem) {
        super(scriptSystem);
        name="exit";
    }

    @Override
    public Object invoke(Object[] args) {
        if(args.length==1){
            if(args[0] instanceof Number) {
                scriptSystem.exit(((Number) args[0]).intValue());
                return null;
            }else{
                ScriptExceptions.exceptionSystemMethodInvoke(this);
            }
        }
        ScriptExceptions.exceptionSystemMethodInvoke(this);
        return null;
    }
}
