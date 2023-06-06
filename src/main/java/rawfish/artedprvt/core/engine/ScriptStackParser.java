package rawfish.artedprvt.core.engine;

import rawfish.artedprvt.core.ScriptStackElement;

public interface ScriptStackParser {
    boolean parseable(Throwable e);
    ScriptStackElement[] parse(Throwable e);
}
