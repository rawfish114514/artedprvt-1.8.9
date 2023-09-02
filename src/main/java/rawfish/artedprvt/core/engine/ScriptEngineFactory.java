package rawfish.artedprvt.core.engine;

import rawfish.artedprvt.core.ScriptProcess;

/**
 * 脚本引擎工厂
 * @param <T>
 */
public interface ScriptEngineFactory<T extends ScriptEngine> {
    T create(ScriptProcess process);
}
