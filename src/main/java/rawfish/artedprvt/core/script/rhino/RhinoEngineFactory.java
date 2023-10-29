package rawfish.artedprvt.core.script.rhino;

import rawfish.artedprvt.core.script.ScriptProcess;
import rawfish.artedprvt.core.script.engine.ScriptEngineFactory;

public class RhinoEngineFactory implements ScriptEngineFactory {
    @Override
    public RhinoEngine create(ScriptProcess process) {
        return new RhinoEngine(process);
    }
}
