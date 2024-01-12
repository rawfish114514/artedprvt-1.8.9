package rawfish.artedprvt.core.app.script.engine;

import rawfish.artedprvt.core.app.script.ScriptProcess;

public interface ScriptStackParserFactory{
    ScriptStackParser create(ScriptProcess process);
}
