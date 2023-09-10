package rawfish.artedprvt.core.rhino;

import rawfish.artedprvt.core.ScriptProcess;
import rawfish.artedprvt.core.engine.ScriptEngineFactory;

public class RhinoEngineFactory implements ScriptEngineFactory {
    @Override
    public RhinoEngine create(ScriptProcess process) {
        return new RhinoEngine(process);
    }
}
