package rawfish.artedprvt.core.engine;

import rawfish.artedprvt.core.ScriptProcess;

public interface ScriptStackParserFactory<T extends ScriptStackParser> {
    T create(ScriptProcess process);
}
