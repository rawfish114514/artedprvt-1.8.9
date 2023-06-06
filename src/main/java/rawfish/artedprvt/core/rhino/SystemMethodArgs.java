package rawfish.artedprvt.core.rhino;

import rawfish.artedprvt.core.ScriptExceptions;
import rawfish.artedprvt.core.ScriptSystem;

public class SystemMethodArgs extends SystemMethod{
    public SystemMethodArgs(ScriptSystem scriptSystem) {
        super(scriptSystem);
        name="args";
    }

    @Override
    public Object invoke(Object[] args) {
        if(args.length>0){
            ScriptExceptions.exceptionSystemMethodInvoke(this);
        }
        return scriptSystem.getArgs();
    }
}
