package rawfish.artedprvt.core.script.engine;

import rawfish.artedprvt.core.script.ScriptStackElement;

public interface ScriptStackParser {
    boolean parseable(Throwable e);
    ScriptStackElement[] parse(Throwable e);
}
