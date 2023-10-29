package rawfish.artedprvt.core.script.rhino;

import rawfish.artedprvt.core.script.ScriptProcess;
import rawfish.artedprvt.core.script.engine.ScriptStackParserFactory;

public class RhinoStackParserFactory implements ScriptStackParserFactory {
    @Override
    public RhinoStackParser create(ScriptProcess process) {
        return new RhinoStackParser();
    }
}
