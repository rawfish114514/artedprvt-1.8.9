package rawfish.artedprvt.core.engine;

import rawfish.artedprvt.core.ScriptLanguage;

public interface Engine{
    String getEngineName();
    ScriptLanguage getScriptLanguage();
    ScriptEngineFactory getScriptEngineFactory();
    ScriptStackParserFactory getScriptStackParserFactory();
    ServiceEngine getServiceEngine();
}
