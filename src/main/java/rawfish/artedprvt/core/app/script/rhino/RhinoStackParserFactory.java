package rawfish.artedprvt.core.app.script.rhino;

import rawfish.artedprvt.core.app.script.ScriptProcess;
import rawfish.artedprvt.core.app.script.engine.ScriptStackParserFactory;

public class RhinoStackParserFactory implements ScriptStackParserFactory {
    @Override
    public RhinoStackParser create(ScriptProcess process) {
        return new RhinoStackParser();
    }
}
