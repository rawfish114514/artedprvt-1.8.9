package rawfish.artedprvt.core.app.script.engine;

import rawfish.artedprvt.core.app.script.ScriptStackElement;

public interface ScriptStackParser {
    boolean parseable(Throwable e);

    ScriptStackElement[] parse(Throwable e);
}
