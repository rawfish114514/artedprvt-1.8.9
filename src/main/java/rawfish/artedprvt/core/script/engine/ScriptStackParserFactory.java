package rawfish.artedprvt.core.script.engine;

import rawfish.artedprvt.core.script.ScriptProcess;

public interface ScriptStackParserFactory{
    ScriptStackParser create(ScriptProcess process);
}
