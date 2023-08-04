package rawfish.artedprvt.core.rhino;

import rawfish.artedprvt.core.ScriptExceptions;
import rawfish.artedprvt.core.ScriptSystem;

public class SystemMethodAssets extends SystemMethod{
    public SystemMethodAssets(ScriptSystem scriptSystem) {
        super(scriptSystem);
        name="assets";
    }

    @Override
    public Object invoke(Object[] args) {
        if(args.length>1){
            ScriptExceptions.exceptionSystemMethodInvoke(this);
        }
        return scriptSystem.getFile("assets/"+String.valueOf(args[0]));
    }
}
