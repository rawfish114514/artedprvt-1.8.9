package rawfish.artedprvt.core.rhino;

import org.mozilla.javascript.Scriptable;
import rawfish.artedprvt.core.ScriptExceptions;
import rawfish.artedprvt.core.ScriptSystem;

public class SystemMethodProps extends SystemMethod {
    public SystemMethodProps(Scriptable scope, ScriptSystem scriptSystem) {
        super(scope,scriptSystem);
        name="props";
    }

    @Override
    public Object invoke(Object[] args) {
        if(args.length>0){
            ScriptExceptions.exception("ses0",this.getName());
        }
        return scriptSystem.getProps();
    }
}
