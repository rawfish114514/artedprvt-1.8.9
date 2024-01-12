package rawfish.artedprvt.core.app.script.rhino;

import rawfish.artedprvt.core.app.script.ScriptProcess;
import rawfish.artedprvt.core.app.script.engine.ScriptEngineFactory;

public class RhinoEngineFactory implements ScriptEngineFactory {
    @Override
    public RhinoEngine create(ScriptProcess process) {
        return new RhinoEngine(process);
    }
}
