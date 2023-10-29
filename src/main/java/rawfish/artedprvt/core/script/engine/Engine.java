package rawfish.artedprvt.core.script.engine;

import rawfish.artedprvt.core.script.ScriptLanguage;

public interface Engine{
    String getEngineName();
    ScriptLanguage getScriptLanguage();
    ScriptEngineFactory getScriptEngineFactory();
    ScriptStackParserFactory getScriptStackParserFactory();
    ServiceEngine getServiceEngine();
}
