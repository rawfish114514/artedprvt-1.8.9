package rawfish.artedprvt.core.app.script.engine;

import rawfish.artedprvt.core.app.script.ScriptProcess;

/**
 * 脚本引擎工厂
 */
public interface ScriptEngineFactory{
    ScriptEngine create(ScriptProcess process);
}
