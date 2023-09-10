package rawfish.artedprvt.core.engine;

import rawfish.artedprvt.core.ScriptProcess;

public interface ScriptStackParserFactory{
    ScriptStackParser create(ScriptProcess process);
}
