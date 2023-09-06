package rawfish.artedprvt.core.rhino;

import org.mozilla.javascript.Scriptable;
import rawfish.artedprvt.core.ScriptExceptions;
import rawfish.artedprvt.core.ScriptSystem;

public class SystemMethodAssetsis extends SystemMethod{
    public SystemMethodAssetsis(Scriptable scope, ScriptSystem scriptSystem) {
        super(scope,scriptSystem);
        name="assetsis";
    }

    @Override
    public Object invoke(Object[] args) {
        if(args.length>1){
            ScriptExceptions.exception("ses0",this.getName());
        }
        return scriptSystem.getFileInputStream("assets/"+String.valueOf(args[0]));
    }
}
