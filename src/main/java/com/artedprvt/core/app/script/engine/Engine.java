package com.artedprvt.core.app.script.engine;

import com.artedprvt.core.app.script.ScriptLanguage;

public interface Engine {
    String getEngineName();

    ScriptLanguage getScriptLanguage();

    ScriptEngineFactory getScriptEngineFactory();

    ScriptStackParserFactory getScriptStackParserFactory();

    ServiceEngine getServiceEngine();
}
