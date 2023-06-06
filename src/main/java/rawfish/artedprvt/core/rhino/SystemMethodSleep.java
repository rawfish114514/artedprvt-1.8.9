package rawfish.artedprvt.core.rhino;

import rawfish.artedprvt.core.ScriptExceptions;
import rawfish.artedprvt.core.ScriptSystem;

public class SystemMethodSleep extends SystemMethod{
    public SystemMethodSleep(ScriptSystem scriptSystem) {
        super(scriptSystem);
        name="sleep";
    }

    @Override
    public Object invoke(Object[] args) {
        if(args.length==1){
            if(args[0] instanceof Number) {
                scriptSystem.sleep(((Number) args[0]).longValue());
                return null;
            }else{
                ScriptExceptions.exceptionSystemMethodInvoke(this);
            }
        }
        ScriptExceptions.exceptionSystemMethodInvoke(this);
        return null;
    }
}
