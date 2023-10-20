package rawfish.artedprvt.core.engine;

import rawfish.artedprvt.core.ScriptProcess;

/**
 * 脚本引擎工厂
 */
public interface ScriptEngineFactory{
    ScriptEngine create(ScriptProcess process);
}
