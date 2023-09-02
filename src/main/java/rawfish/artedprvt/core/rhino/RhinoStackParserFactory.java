package rawfish.artedprvt.core.rhino;

import rawfish.artedprvt.core.ScriptProcess;
import rawfish.artedprvt.core.engine.ScriptStackParserFactory;

public class RhinoStackParserFactory implements ScriptStackParserFactory<RhinoStackParser> {
    @Override
    public RhinoStackParser create(ScriptProcess process) {
        return new RhinoStackParser();
    }
}
