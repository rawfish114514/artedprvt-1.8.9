package rawfish.artedprvt.core.rhino;

import org.mozilla.javascript.RhinoException;
import rawfish.artedprvt.core.ScriptStackElement;
import rawfish.artedprvt.core.engine.ScriptStackParser;

public class RhinoScriptStackParser implements ScriptStackParser {
    @Override
    public boolean parseable(Throwable e) {
        return e instanceof RhinoException;
    }

    @Override
    public ScriptStackElement[] parse(Throwable e) {
        RhinoException rhinoException=(RhinoException)e;
        org.mozilla.javascript.ScriptStackElement[] jses=rhinoException.getScriptStack();
        org.mozilla.javascript.ScriptStackElement jse;
        ScriptStackElement[] stackElements=new ScriptStackElement[jses.length];
        for(int i=0;i<jses.length;i++){
            jse=jses[i];
            stackElements[i]=new ScriptStackElement(jse.fileName,jse.lineNumber);
        }
        return stackElements;
    }
}
