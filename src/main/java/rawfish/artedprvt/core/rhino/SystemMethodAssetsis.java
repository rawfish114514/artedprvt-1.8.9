package rawfish.artedprvt.core.rhino;

import rawfish.artedprvt.core.ScriptExceptions;
import rawfish.artedprvt.core.ScriptSystem;

public class SystemMethodAssetsis extends SystemMethod{
    public SystemMethodAssetsis(ScriptSystem scriptSystem) {
        super(scriptSystem);
        name="assetsis";
    }

    @Override
    public Object invoke(Object[] args) {
        if(args.length>1){
            ScriptExceptions.exceptionSystemMethodInvoke(this);
        }
        return scriptSystem.getFileInputStream("assets/"+String.valueOf(args[0]));
    }
}
