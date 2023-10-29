package rawfish.artedprvt.core.script.engine;

import rawfish.artedprvt.core.script.ScriptProcess;

/**
 * 脚本引擎工厂
 */
public interface ScriptEngineFactory{
    ScriptEngine create(ScriptProcess process);
}
