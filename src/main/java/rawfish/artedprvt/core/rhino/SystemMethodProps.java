package rawfish.artedprvt.core.rhino;

import rawfish.artedprvt.core.ScriptExceptions;
import rawfish.artedprvt.core.ScriptSystem;

public class SystemMethodProps extends SystemMethod {
    public SystemMethodProps(ScriptSystem scriptSystem) {
        super(scriptSystem);
        name="props";
    }

    @Override
    public Object invoke(Object[] args) {
        if(args.length>0){
            ScriptExceptions.exceptionSystemMethodInvoke(this);
        }
        return scriptSystem.getProps();
    }
}
