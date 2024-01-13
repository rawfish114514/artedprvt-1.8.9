package rawfish.artedprvt.core.app.script.rhino;

import rawfish.artedprvt.core.app.script.ScriptLanguage;
import rawfish.artedprvt.core.app.script.engine.Engine;
import rawfish.artedprvt.core.app.script.engine.Engines;
import rawfish.artedprvt.core.app.script.engine.ScriptEngineFactory;
import rawfish.artedprvt.core.app.script.engine.ScriptStackParserFactory;
import rawfish.artedprvt.core.app.script.engine.ServiceEngine;

public class Rhino implements Engine {
    static final String NAME = "Rhino";
    static final ScriptLanguage LANGUAGE = new ScriptLanguage("js");
    static final RhinoEngineFactory ENGINE_FACTORY = new RhinoEngineFactory();
    static final RhinoStackParserFactory STACK_PARSER_FACTORY = new RhinoStackParserFactory();
    static final RhinoServiceEngine SERVICE_ENGINE = new RhinoServiceEngine();

    public static void init() {
        Engines.reg(new Rhino());
    }

    @Override
    public String getEngineName() {
        return NAME;
    }

    @Override
    public ScriptLanguage getScriptLanguage() {
        return LANGUAGE;
    }

    @Override
    public ScriptEngineFactory getScriptEngineFactory() {
        return ENGINE_FACTORY;
    }

    @Override
    public ScriptStackParserFactory getScriptStackParserFactory() {
        return STACK_PARSER_FACTORY;
    }

    @Override
    public ServiceEngine getServiceEngine() {
        return SERVICE_ENGINE;
    }
}
