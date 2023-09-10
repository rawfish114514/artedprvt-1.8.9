package rawfish.artedprvt.core.rhino;

import rawfish.artedprvt.core.ScriptLanguage;
import rawfish.artedprvt.core.engine.*;

public class Rhino implements Engine{
    static final String NAME="Rhino";
    static final ScriptLanguage LANGUAGE=new ScriptLanguage("js");
    static final RhinoEngineFactory ENGINE_FACTORY=new RhinoEngineFactory();
    static final RhinoStackParserFactory STACK_PARSER_FACTORY=new RhinoStackParserFactory();
    static final RhinoServiceEngine SERVICE_ENGINE=new RhinoServiceEngine();

    public static void init(){
        ClassCollection.load(McpToSrgString.getMcpToSrgString());
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
